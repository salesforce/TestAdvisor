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
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.net.Proxy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import com.salesforce.selenium.support.event.EventFiringWebDriver;
import com.saucelabs.saucerest.SauceREST;

import org.testng.Assert;

/**
 * Factory for creating an {@link EventFiringWebDriver} which works in Salesforce Central QE environment.
 */
public class WebDriverFactory {
	/**
	 * Instantiates a WebDriver instance for the test context defined in testcontext.json. This file has
	 * to be present in the root of the test directory.
	 * 
	 * @param testName name of the current test
	 * @return WebDriver instance
	 */
	public static WebDriver getWebDriver(String testName) {
		TestContext testContext = null;
		try {
			testContext = TestContext.getContext();
		} catch (MalformedJsonException mje) {
			Assert.fail("Could not get test context information from JSON file " + TestContext.JSON_FILENAME);
		}

		TestContext.Type context = testContext.getContextType();
		Browser browser = testContext.getBrowser();
		String sauceName = testContext.getSauceLab_userName();
		String sauceKey = testContext.getSauceLab_accessKey();

		DesiredCapabilities caps = new DesiredCapabilities();
		WebDriver driver = null;

		String proxyUrl = testContext.getOs_proxy_url();
		caps.setCapability("browserName", testContext.getBrowser().toString());
		// For Firefox: version 54.0 generated lots of "UnsupportedCommandException: mouseMoveTo" errors :(
		caps.setCapability("version", testContext.getBrowser_version());
		caps.setCapability("screenResolution", testContext.getBrowser_screenResolution());
		caps.setCapability("timeZone", testContext.getOs_timeZone());
		caps.setCapability("name", testName);

		// Set the Jenkins build value, if available
		String jobName = System.getenv("JOB_NAME");
		if (StringUtils.isNotBlank(jobName)) {
			String buildNumber = System.getenv("BUILD_NUMBER");
			String jenkinsBuild = jobName + ":" + buildNumber;
			caps.setCapability("build", jenkinsBuild);
		}

		switch (context) {
		case saucelabs:
			System.out.println("Connecting to saucelabs.");
			String URL = "https://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:443/wd/hub";
			// SacueLabs allows to choose the platform to run on
			caps.setCapability("platform", testContext.getOs_platform());

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
				caps.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
				// Cannot use ChromeOptions because it's not available in WebDriver v2
				driver = new ChromeDriver(caps);
			} else {
				// Cannot use FireFoxOptions because it's not available in WebDriver v2
				driver = new FirefoxDriver(caps);
			}
			driver.manage().window().setSize(testContext.getScreenResolutionAsDimension());
			break;
		default:
			throw new IllegalArgumentException("Unknown or unsupported context " + context.name());
		}

		if (testContext.getImplicitTimeout() != -1) {
			driver.manage().timeouts().implicitlyWait(testContext.getImplicitTimeout(), TimeUnit.SECONDS);
		}

		EventFiringWebDriver wd = new EventFiringWebDriver(driver, testName);
		wd.register(new PerformanceListener());
		wd.register(new Log2TestCase());
		return wd;
	}

	/**
	 * Used for propagating the test results to SauceLabs.
	 * @param hasPassed
	 * @param driver
	 */
	public static void setPassed(boolean hasPassed, WebDriver driver) {
		TestContext testContext = null;
		try {
			testContext = TestContext.getContext();
		} catch (MalformedJsonException mje) {
			// highly unlikely because a problem will already have lead to a fail() in getWebDriver(..)
			Assert.fail("Could not get test context information from JSON file " + TestContext.JSON_FILENAME);
		}

		TestContext.Type context = testContext.getContextType();
		if (context == TestContext.Type.saucelabs) {
			String jobId = null;
			if (driver instanceof EventFiringWebDriver) {
				WebDriver wrappedDriver = ((EventFiringWebDriver) driver).getWrappedDriver();
				if (wrappedDriver instanceof RemoteWebDriver) {
					jobId = ((RemoteWebDriver) wrappedDriver).getSessionId().toString();
				}
			}

			if (jobId != null) { 
				System.out.println("WebDriverFactory.setPassed: session " + jobId + " set to " + hasPassed);
			} else {
				System.out.println("WebDriverFactory.setPassed: session <unknown> set to " + hasPassed);
			}

			// Need to respect proxy if present.
			SauceREST saucer;
			if (StringUtils.isNotBlank(testContext.getOs_proxy_url())) {
				try {
					saucer = new ProxiedSauce(testContext.getSauceLab_userName(), testContext.getSauceLab_accessKey(), testContext.getOs_proxy_url());
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
			} else {
				saucer = new SauceREST(testContext.getSauceLab_userName(), testContext.getSauceLab_accessKey());
			}
			if (jobId != null) {
				if (hasPassed) {
					saucer.jobPassed(jobId);
				} else {
					saucer.jobFailed(jobId);
				}
			}
		}
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
}
