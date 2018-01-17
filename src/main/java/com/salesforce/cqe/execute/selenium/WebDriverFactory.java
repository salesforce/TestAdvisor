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

import com.salesforce.selenium.support.event.EventFiringWebDriver;
import com.salesforce.selenium.support.event.Log2TestCase;
import com.saucelabs.saucerest.SauceREST;
import org.testng.ITestResult;

/**
 * Factory for creating an {@link EventFiringWebDriver} which works in Salesforce Central QE environment.
 */
public class WebDriverFactory {
	public enum Browser {
		Chrome, IE, Firefox, Safari
	};

	// Probably should pull from environment
	static String getSauceName() {
		// return "tstarbow";
		return "gneumann";
	}

	// Probably should pull from environment
	static String getSauceKey() {
		// return "cf312d48-6250-40bf-82ad-a70991bdec72"; // Tao
		return "4608db0d-b261-48ba-8044-a0eea4291ea4"; // Georg
	}

	static String getBrowser() {
		return System.getProperty("testcontext.browser");
	}

	static String getContext() {
		return System.getProperty("testcontext.remote");
	}

	static String getProxyUrl() {
		return System.getProperty("testcontext.proxy_url"); // public0-proxy1-0-prd.data.sfdc.net:8080
	}

	public static WebDriver getWebDriver(String testName) {
		String browserProp = getBrowser();
		if (browserProp == null) {
			browserProp = Browser.Firefox.name(); // Default.
		}
		Browser browser = Browser.valueOf(browserProp);
		String sauceName = getSauceName();
		String sauceKey = getSauceKey();

		DesiredCapabilities caps = new DesiredCapabilities();
		WebDriver driver;
		String context = getContext();
		String proxyUrl = getProxyUrl();
		if (browser == Browser.Firefox) {
			caps.setCapability("browserName", "firefox");
			// caps.setCapability("version", "54.0"); -- Generated lots of
			// "UnsupportedCommandException: mouseMoveTo" errors :(
			caps.setCapability("version", "45.0");
		} else if (browser == Browser.Chrome) {
			caps.setCapability("browserName", "chrome");
			caps.setCapability("version", "60.0");
		}

		if (StringUtils.equals(context, "saucelabs")) {
			System.out.println("Connecting to saucelabs.");
			String URL = "https://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:443/wd/hub";
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("screenResolution", "1920x1200");
			caps.setCapability("timeZone", "Los Angeles");
			caps.setCapability("name", "Schneider:" + testName);

			// Are we running in Jenkins? If so, set the Build value.
			String jobName = System.getenv("JOB_NAME");
			if (StringUtils.isNotBlank(jobName)) {
				String buildNumber = System.getenv("BUILD_NUMBER");
				String jenkinsBuild = jobName + ":" + buildNumber;
				caps.setCapability("build", jenkinsBuild);
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
		} else {
			if (StringUtils.equals(context, "privatecloud")) {
				org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(proxyUrl).setSslProxy(proxyUrl);
				proxy.setFtpProxy(proxyUrl).setSocksProxy(proxyUrl);
				caps.setCapability(CapabilityType.PROXY, proxy);
				System.out.println("DEBUG: Setting up browser to use the proxy: " + proxyUrl);
			}
			// Get a local browser.
			if (browser == Browser.Chrome) {
				caps.setCapability("version", "63.0");
				caps.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
				driver = new ChromeDriver(caps);
			} else {
				driver = new FirefoxDriver(caps);
			}
		}
		EventFiringWebDriver wd = new EventFiringWebDriver(driver, testName);
		wd.register(new Log2TestCase());
		return wd;
	}

	/**
	 * Used for propagating the test results to SauceLabs, using their REST API.
	 * 
	 * @param passed
	 * @throws URISyntaxException
	 */
	public static void setPassed(ITestResult result, WebDriver driver) {
		String context = getContext();
		if (StringUtils.equals(context, "saucelabs")) {
			boolean passed = result.getStatus() == ITestResult.SUCCESS;
			String jobId = ((RemoteWebDriver) driver).getSessionId().toString();
			System.out.println("BaseExcutionClass.setPassed: " + jobId + " set to " + passed);
			// Need to respect proxy if present.
			SauceREST saucer;
			if (StringUtils.isNotBlank(getProxyUrl())) {
				try {
					saucer = new ProxiedSauce(getSauceName(), getSauceKey(), getProxyUrl());
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
			} else {
				saucer = new SauceREST(getSauceName(), getSauceKey());
			}
			if (passed) {
				saucer.jobPassed(jobId);
			} else {
				saucer.jobFailed(jobId);
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

//		@Override
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
