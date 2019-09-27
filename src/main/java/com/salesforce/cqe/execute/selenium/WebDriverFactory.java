/* 
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license. 
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.cqe.execute.selenium;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.net.Proxy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpClient.Factory;
import org.openqa.selenium.remote.internal.ApacheHttpClient;

import com.google.gson.stream.MalformedJsonException;
import com.salesforce.cqe.common.TestContext;
import com.salesforce.cqe.common.TestContext.Browser;
import com.salesforce.cqe.common.TestContext.Env;
import com.salesforce.selenium.support.event.EventFiringWebDriver;
import com.saucelabs.saucerest.SauceREST;

import org.testng.Assert;

/**
 * Factory for creating an {@link EventFiringWebDriver} which works in Salesforce Central QE environment.
 */
public class WebDriverFactory {
	/**
	 * Instantiates a WebDriver instance for the test context defined under "selenium"/"jenkins"|"local"
	 * in testcontext.json. This file has to be present in the root of the test directory.
	 * 
	 * @param testName name of the current test
	 * @return WebDriver instance
	 */
	public synchronized static WebDriver getWebDriver(String testName) {
		Env env = getSeleniumTestContext();
		DesiredCapabilities caps = new DesiredCapabilities();

		// Set env and the Jenkins build value, if available
		if (isRunningOnJenkins()) {
			String buildNumber = System.getenv("BUILD_NUMBER");
			String jenkinsBuild = System.getenv("JOB_NAME") + ":" + buildNumber;
			caps.setCapability("build", jenkinsBuild);
		}
		
		Browser browser = env.getBrowser();
		String proxyUrl = env.getOsProxyUrl();
		caps.setCapability("browserName", env.getBrowser().toString());
		caps.setCapability("version", env.getBrowserVersion());
		caps.setCapability("screenResolution", env.getBrowserScreenResolution());
		caps.setCapability("timeZone", env.getOsTimeZone());
		caps.setCapability("name", testName);

		WebDriver driver = null;
		switch (env.getContextType()) {
		case saucelabs:
			String sauceName = env.getSauceLabUserName();
			String sauceKey = env.getSauceLabAccessKey();

			printMsg("Connecting to saucelabs.");
			String URL = "https://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:443/wd/hub";
			// SauceLabs allows to choose the platform to run on
			caps.setCapability("platform", env.getOsPlatform());
			caps.setCapability("extendedDebugging", true);
			caps.setCapability("maxDuration",env.getSauceLabMaxDuration());
			if (browser == Browser.chrome) {
				caps.setCapability(ChromeOptions.CAPABILITY, disableShowNotificationsForChrome());
			} else if (browser == Browser.firefox) {
				disableShowNotificationsForFirefox().merge(caps);
			}

			try { // Promote to runtime exception since wrapping function is not setup to handle.
				if (StringUtils.isBlank(proxyUrl)) {
					driver = new RemoteWebDriver(new URL(URL), caps);
				} else { // Use the proxy to get to saucelabs.
					Factory factory = new ProxiedHttpClientFactory(proxyUrl);
					HttpCommandExecutor executor = new HttpCommandExecutor(new HashMap<String, CommandInfo>(),
							new URL(URL), factory);
					driver = new RemoteWebDriver(executor, caps);
				}
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			break;
		case privatecloud:
			if (StringUtils.isEmpty(proxyUrl)) {
				throw new IllegalArgumentException("Proxy needed in PrivateCloud");
			}
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setHttpProxy(proxyUrl).setSslProxy(proxyUrl);
			proxy.setFtpProxy(proxyUrl).setSocksProxy(proxyUrl);
			caps.setCapability(CapabilityType.PROXY, proxy);
			// no BREAK here by design!
		case local:
			if (browser == Browser.chrome) {
				driver = new ChromeDriver(disableShowNotificationsForChrome().merge(caps));
			} else if (browser == Browser.firefox) {
				driver = new FirefoxDriver(disableShowNotificationsForFirefox().merge(caps));
			}
			driver.manage().window().setSize(env.getBrowserScreenResolutionAsDimension());
			break;
		default:
			// can't happen because validation was already done during reading json file
		}

		if (env.getBrowserImplicitTimeout() != -1) {
			driver.manage().timeouts().implicitlyWait(env.getBrowserImplicitTimeout(), TimeUnit.SECONDS);
		}

		EventFiringWebDriver wd = new EventFiringWebDriver(driver, testName);
		wd.register(new PerformanceListener());
		wd.register(new StepsToReproduce(testName));
		if ("yes".equalsIgnoreCase(System.getProperty(ShadowJSPathGenerator.RECORD_SHADOWJSPATH, ""))) {
			wd.register(new ShadowJSPathGenerator(driver, testName));
		}
		return wd;
	}

	private static ChromeOptions disableShowNotificationsForChrome() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	private static FirefoxOptions disableShowNotificationsForFirefox() {
		FirefoxOptions options = new FirefoxOptions();
		options.addPreference("permissions.default.desktop-notification", 1);
		return options;
	}

	/**
	 * Propagates the test results to SauceLabs by setting the job status.
	 * @param hasPassed true if test has passed, otherwise false
	 * @param driver WebDriver instance currently driving the test
	 * @return false if setting SauceLabs job status failed; otherwise true
	 */
	public synchronized static boolean setPassed(boolean hasPassed, WebDriver driver) {
		boolean isReportedSuccessfully = true;
		Env env = getSeleniumTestContext();

		if (env.getContextType() == TestContext.Type.saucelabs) {
			String jobId = null;
			WebDriver wrappedDriver = driver;
			if (driver instanceof EventFiringWebDriver) {
				wrappedDriver = ((EventFiringWebDriver) driver).getWrappedDriver();
			}

			if (wrappedDriver instanceof RemoteWebDriver) {
				jobId = ((RemoteWebDriver) wrappedDriver).getSessionId().toString();
			}

			if (jobId != null) { 
				printMsg("WebDriverFactory.setPassed: session " + jobId + " set to " + hasPassed);
			} else {
				printMsg("WebDriverFactory.setPassed: session <unknown> set to " + hasPassed);
			}

			// Need to respect proxy if present.
			SauceREST saucer;
			if (StringUtils.isNotBlank(env.getOsProxyUrl())) {
				try {
					saucer = new ProxiedSauce(env.getSauceLabUserName(), env.getSauceLabAccessKey(), env.getOsProxyUrl());
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
			} else {
				saucer = new SauceREST(env.getSauceLabUserName(), env.getSauceLabAccessKey());
			}
			if (jobId != null) {
				try {
					if (hasPassed) {
						saucer.jobPassed(jobId);
					} else {
						saucer.jobFailed(jobId);
					}
				} catch (Exception e) {
					// The webDriver instance has either already been shutdown and can't take any
					// further commands, or has hit a different exception like SocketTimeoutException.
					// Just log this situation and prevent exception from bubbling up. Further actions
					// are neither necessary nor possible.
					printMsg(e.getMessage());
					isReportedSuccessfully = false;
				}
			}
		}
		return isReportedSuccessfully;
	}
	
	public static TestContext.Env getSeleniumTestContext() {
		TestContext testContext = null;

		try {
			testContext = TestContext.getContext();
		} catch (MalformedJsonException mje) {
			Assert.fail("Could not get test context information from JSON file " + TestContext.JSON_FILENAME);
		}

		return isRunningOnJenkins() ? testContext.getSeleniumEnvs().getJenkins() : testContext.getSeleniumEnvs().getLocal();
	}

	private static class ProxiedHttpClientFactory implements org.openqa.selenium.remote.http.HttpClient.Factory {
		final HttpClient client;

		public ProxiedHttpClientFactory(String proxyInfo) {
			HttpHost proxy = HttpHost.create(proxyInfo);
			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setProxy(proxy);
			this.client = builder.build();
		}

		@Override
		public org.openqa.selenium.remote.http.HttpClient createClient(URL url) {
			return new ApacheHttpClient(client, url);
		}

		// Don't tag with @Override to allow code to run with older Selenium versions  
//		@Override
		public void cleanupIdleClients() {
			; // no-op
		}
	}

	private static class ProxiedSauce extends SauceREST {
		private static final long serialVersionUID = 1L;
		private String proxyHost;
		private int proxyPort;

		public ProxiedSauce(String username, String accessKey, String proxyURI) throws URISyntaxException {
			super(username, accessKey);
			URI uri = new URI("https://" + proxyURI);
			proxyHost = uri.getHost();
			proxyPort = uri.getPort();
		}

		@Override
		public HttpURLConnection openConnection(URL url) throws IOException {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
			con.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
			con.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
			return con;
		}
	}

	private static boolean isRunningOnJenkins() {
		return StringUtils.isNotBlank(System.getenv("JOB_NAME"));
	}

	private static void printMsg(String msg) {
		System.out.printf("[%d] %s%n", Thread.currentThread().getId(), msg);
	}
}
