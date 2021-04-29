/* 
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license. 
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.cqe.execute.selenium;

import com.google.gson.stream.MalformedJsonException;
import com.salesforce.cqe.common.RestUtil;
import com.salesforce.cqe.common.TestContext;
import com.salesforce.cqe.common.TestContext.Browser;
import com.salesforce.cqe.common.TestContext.Env;
import com.salesforce.cqe.common.TestContext.Platform;
import com.salesforce.cqe.common.pojo.SaucelabsVMConcurrencyResponse;
import com.salesforce.dropin.common.BaseData;
import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.EventFiringWebDriver;
import com.salesforce.selenium.support.event.WebDriverEventListener;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpClient.Factory;
import org.openqa.selenium.remote.internal.OkHttpClient;
import org.testng.Assert;

import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Factory for creating an {@link EventFiringWebDriver} which works in Salesforce Central QE environment.
 */
public class WebDriverFactory {
	private static String hub, port;

	/**
	 * Instantiates a WebDriver instance for the test context defined under
	 * "selenium"/"jenkins"|"local" in testcontext.json. This file has to be present
	 * in the root of the test directory.
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
		Platform platform = env.getPlatform();

		if (platform == Platform.desktop) {
			caps.setCapability("browserName", env.getBrowser().toString());
			caps.setCapability("version", env.getBrowserVersion());
			caps.setCapability("screenResolution", env.getBrowserScreenResolution());
		}

		caps.setCapability("timeZone", env.getOsTimeZone());
		caps.setCapability("name", testName);

		// enable logs provided by Selenium
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		logs.enable(LogType.CLIENT, Level.ALL);
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.PERFORMANCE, Level.ALL);
		logs.enable(LogType.PROFILER, Level.ALL);
		logs.enable(LogType.SERVER, Level.ALL);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logs);

		WebDriver driver = null;
		switch (env.getContextType()) {
			case saucelabs:

				String sauceName = env.getSauceLabUserName();
				String sauceKey = env.getSauceLabAccessKey();
				// saucelabs url to fetch concurrent virtual machines (VM) status
				String saucelabsConcurrencyurl = "https://saucelabs.com/rest/v1.2/users/" + sauceName + "/concurrency";
				// only fetch concurrent VM's status if test is being executed from local
				if (!isRunningOnJenkins()) {
					SaucelabsVMConcurrencyResponse saucelabsVMConcurrencyResponse;
					try {
						// fetch number of allowed and utilised VM's number using saucelabs rest api and deserialize the response
						saucelabsVMConcurrencyResponse = RestUtil.getResponseWithBasicAuth(saucelabsConcurrencyurl, sauceName, sauceKey).as(SaucelabsVMConcurrencyResponse.class);
					} catch (Exception ex) {
						// exception will be thrown in case expected response is not received and deserialization will fail. Further execution won't be possible.
						throw new RuntimeException(ex);
					}

					int utilizedVMs = saucelabsVMConcurrencyResponse.getConcurrency().getOrganization().getCurrent().getVms();
					int allowedVMs = saucelabsVMConcurrencyResponse.getConcurrency().getOrganization().getAllowed().getVms();
					int numberOfReservedVms = 2;
					// allowedVMs-2 to cover the case where two currently running builds have just finished a job and are about to start the next test cases.
					// I will review it in future and change accordingly
					allowedVMs = allowedVMs-numberOfReservedVms;
					System.out.println("Saucelabs VM stats - utilizedVMs count: " + utilizedVMs + ", allowedVM's count: " + allowedVMs +
							" \n * " + numberOfReservedVms + " VM's are reserved to cover the case where two currently running builds have just finished a job and are about to start the next test cases.");
					// restrict the execution of test if all the allowed VM's are utilized.
					if (utilizedVMs > allowedVMs) {
						// exception will be thrown to prevent test to execute.
						throw new RuntimeException("All allowed (" + utilizedVMs + " VM's are already utilized. Please try to execute local tests on saucelabs after sometime.");
					}
				}

			String URL;
			if(env.getSauceLabUseHttps()){
				printMsg("Connecting to saucelabs over HTTPS.");
				URL = "https://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:443/wd/hub";
			}
			else{
				printMsg("Connecting to saucelabs over HTTP.");
				URL = "http://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:80/wd/hub";
			}

			caps.setCapability("username",sauceName);
			caps.setCapability("accessKey",sauceKey);

			if (platform == Platform.desktop) {
				// SauceLabs allows to choose the platform to run on
				caps.setCapability("platform", env.getOsPlatform());
				caps.setCapability("commandTimeout", env.getSauceLabCommandTimeout());
				caps.setCapability("idleTimeout", env.getSauceLabIdleTimeout());
				caps.setCapability("extendedDebugging", true);
				caps.setCapability("maxDuration",env.getSauceLabMaxDuration());
				disableBrowserNotification(caps, browser);
			} else if (platform==Platform.ios || platform == Platform.android) {
				//set common mobile caps
				caps.setCapability("appiumVersion",env.getAppiumVersion());
				caps.setCapability("deviceName", env.getDeviceName());
				caps.setCapability("deviceOrientation", env.getDeviceOrientation());
				caps.setCapability("platformVersion",env.getPlatformVersion());
				caps.setCapability("app", env.getAppBinary());
				caps.setCapability("language","en");
				//Platform specific caps
				if (platform ==Platform.ios) {
					caps.setCapability("platformName","iOS");
					caps.setCapability("automationName","XCUITest");
					caps.setCapability("calendarAccessAuthorized",true);
					caps.setCapability("webkitResponseTimeout",180000);
					caps.setCapability("autoAcceptAlerts",false);
					caps.setCapability("nativeWebTap",true);
				} else if (platform == Platform.android){
					caps.setCapability("deviceType", "phone");
					caps.setCapability(MobileCapabilityType.FULL_RESET, false);
					caps.setCapability("autoGrantPermissions",false);
					caps.setCapability("locale","US");
					caps.setCapability("automationName","UIAutomator2");
					caps.setCapability("appPackage","com.salesforce.chatter");
					caps.setCapability("appActivity","com.salesforce.chatter.Chatter");
					caps.setCapability("platformName","Android");
					caps.setCapability("nativeWebScreenshot", true);
					caps.setCapability("disableAndroidWatchers", true);
					caps.setCapability("webviewSupport", true);
				}
			}

			try { // Promote to runtime exception since wrapping function is not setup to handle.
				if (StringUtils.isBlank(proxyUrl) ) {
					switch (platform) {
						case ios:
							driver = new IOSDriver<>(new URL(URL), caps);
							break;
						case android:
							driver = new AndroidDriver<>(new URL(URL), caps);
							break;
						case desktop:
							driver = new RemoteWebDriver(new URL(URL), caps);
							break;
						default:
							// can't happen because validation was already done during reading json file
					}
				}else { // Use the proxy to get to saucelabs.
					String proxyHost;
					int proxyPort;
					try {
						URI uri = new URI("https://" + proxyUrl);
						proxyHost = uri.getHost();
						proxyPort = uri.getPort();
					}
					catch (URISyntaxException e) {
						throw new RuntimeException(e);
					}
					okhttp3.OkHttpClient.Builder client = new okhttp3.OkHttpClient.Builder()
							.connectTimeout(60, TimeUnit.SECONDS)
							.writeTimeout(60, TimeUnit.SECONDS)
							.readTimeout(60, TimeUnit.SECONDS)
							.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost,proxyPort)));
					Factory factory = new ProxiedHttpClientFactory(new OkHttpClient(client.build(),new URL(URL)));
					HttpCommandExecutor executor = new HttpCommandExecutor(new HashMap<>(),
							new URL(URL), factory);
					switch (platform) {
						case ios:
							driver = new IOSDriver<>(executor, caps);
							break;
						case android:
							driver = new AndroidDriver<>(executor, caps);
							break;
						case desktop:
							driver = new RemoteWebDriver(executor, caps);
							break;
						default:
							// can't happen because validation was already done during reading json file
					}
				}
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			break;
		case privatecloud:
			if (StringUtils.isEmpty(proxyUrl)) {
				throw new IllegalArgumentException("Proxy needed in PrivateCloud");
			}

			hub = EventFiringWebDriver.getProperty("HUB_HOST", "10.233.160.148");
			port = EventFiringWebDriver.getProperty("HUB_PORT", "4444");
			setBaaSCapabilities(caps, testName, proxyUrl);
			disableBrowserNotification(caps, browser);

			try {
				driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub",hub,port)), caps);
				driver.manage().window().maximize();
				printMsg(testName + " test video link:" + "http://" + hub + ":8080/video/" + caps.getCapability("videoName"));
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			break;
		case local:
			String driverVersion = EventFiringWebDriver.getProperty("driver.version", null);
			if (browser == Browser.chrome) {
				if (driverVersion == null) {
					WebDriverManager.chromedriver().setup();
				} else {
					WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
				}
				driver = new ChromeDriver(disableShowNotificationsForChrome().merge(caps));
			} else if (browser == Browser.firefox) {
				if (driverVersion == null) {
					WebDriverManager.firefoxdriver().setup();
				} else {
					WebDriverManager.firefoxdriver().driverVersion(driverVersion).setup();
				}
				driver = new FirefoxDriver(disableShowNotificationsForFirefox().merge(caps));
			}
			driver.manage().window().setSize(env.getBrowserScreenResolutionAsDimension());
			break;
		case docker:
			printMsg("Connecting to a localhost baas selenium grid.");
			try{
				hub = EventFiringWebDriver.getProperty("HUB_HOST", "127.0.0.1");
				port = EventFiringWebDriver.getProperty("HUB_PORT", "4444");
				setBaaSCapabilities(caps, testName, proxyUrl);
				disableBrowserNotification(caps, browser);
				driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub",hub,port)), caps);
				driver.manage().window().maximize();
				printMsg(testName + " test video link:" +  "http://" + hub + ":8080/video/" + caps.getCapability("videoName"));
			}catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}

		default:
			// can't happen because validation was already done during reading json file
		}

		if (env.getBrowserImplicitTimeout() != -1) {
			driver.manage().timeouts().implicitlyWait(env.getBrowserImplicitTimeout(), TimeUnit.SECONDS);
		}

		if (platform == Platform.desktop) {
			// Only desktop supports the event firing webdriver
			EventFiringWebDriver wd = new EventFiringWebDriver(driver, testName);
			wd.register(new PerformanceListener());
			wd.register(new StepsToReproduce(testName));
			wd.register(new LogLocators(testName));
			if ("yes".equalsIgnoreCase(EventFiringWebDriver.getProperty(ShadowJSPathGenerator.RECORD_SHADOWJSPATH, ""))) {
				wd.register(new ShadowJSPathGenerator(driver, testName));
			}
			return wd;
		} else {
			return driver;
		}
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
		writeAllLogFiles(driver);

		boolean isReportedSuccessfully = true;
		Env env = getSeleniumTestContext();

		// test execution is on saucelabs
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
		final OkHttpClient okHttpClient;

		public ProxiedHttpClientFactory(OkHttpClient okHttpClient) {
			this.okHttpClient = okHttpClient;
		}

		@Override
		public org.openqa.selenium.remote.http.HttpClient createClient(URL url) {
			// TODO Auto-generated method stub
			return okHttpClient;
		}

		// Don't tag with @Override to allow code to run with older Selenium versions
//		@Override
		public void cleanupIdleClients() {
			; // no-op
		}

		@Override
		public HttpClient.Builder builder() {
			return null;
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

	public static void setBaaSCapabilities(DesiredCapabilities caps, String testName, String proxyUrl) {
		Env env = getSeleniumTestContext();
		String jenkinsBuild = null;
		String[] splitTestName = testName.split("\\.");
		String shortenTestName = splitTestName[splitTestName.length-1];
		if (isRunningOnJenkins()) {
			String buildNumber = System.getenv("BUILD_NUMBER");
			jenkinsBuild = System.getenv("JOB_NAME") + ":" + buildNumber;
			// append test case and video name with jenkins jobname and build.
			caps.setCapability("name", jenkinsBuild + "_" + testName);
			caps.setCapability("videoName", jenkinsBuild + "_" + shortenTestName + ".mp4");
			caps.setCapability("logName", jenkinsBuild + "_" + shortenTestName + ".log");
		} else {
			caps.setCapability("name", "Local_" + testName);
			caps.setCapability("videoName", "Local_" + getSystemDateByTimezone(env.getOsTimeZone(),"") + "_" + shortenTestName + ".mp4");
			caps.setCapability("logName", "Local_" + getSystemDateByTimezone(env.getOsTimeZone(),"") + "_" + shortenTestName + ".log");
		}

		if(env.getContextType().equals(TestContext.Type.privatecloud) || isRunningOnJenkins()) {
			org.openqa.selenium.Proxy publicProxy = new org.openqa.selenium.Proxy();
			publicProxy.setSslProxy(proxyUrl);
			publicProxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
			ChromeOptions options = new ChromeOptions();
			options.setCapability("proxy", publicProxy);
			caps.setCapability(ChromeOptions.CAPABILITY, options);
		}

		printMsg("Connecting to a grid at " + hub + " and port " + port);
		caps.setCapability("enableVNC", true);
		caps.setCapability("enableVideo", true);
		caps.setCapability("enableLog", true);
	}

	public static String getSystemDateByTimezone(String timezone, String format) {
		if(format == "" || format == null || format.isEmpty())
			format = "yyyy-MM-dd'T'HH:mm:ss";
		DateTime dt = new DateTime(System.currentTimeMillis());
		DateTime timezoneDT = dt.withZone(DateTimeZone.forID(timezone));
		return timezoneDT.toString(format);
	}

	public static void disableBrowserNotification(DesiredCapabilities caps, Browser browser) {
		if (browser == Browser.chrome) {
			caps.setCapability(ChromeOptions.CAPABILITY, disableShowNotificationsForChrome());
		} else if (browser == Browser.firefox) {
			disableShowNotificationsForFirefox().merge(caps);
		}
	}

	private static void writeAllLogFiles(WebDriver driver) {
		// As of Dec 1st, 2020, gecko driver does not supports getting logs
		if (getSeleniumTestContext().getBrowser() == Browser.firefox)
			return;

		String testName = "not available";
		WebDriver tmpDriver = driver;
		if (driver instanceof EventFiringWebDriver) {
			tmpDriver = ((EventFiringWebDriver) driver).getWrappedDriver();
			BaseData<String> data = ((EventFiringWebDriver) driver).getDataStore();
			String temp = data.getData(EventFiringWebDriver.WebDriverConfigData.KEY_TESTNAME);
			if (temp != null)
				testName = temp;
		}
		final Logs logs = tmpDriver.manage().logs();
		try {
			Iterator<String> logTypesIterator = logs.getAvailableLogTypes().iterator();
			while (logTypesIterator.hasNext()) {
				writeLogFile(logs, logTypesIterator.next(), testName);
			}
		} catch (UnsupportedCommandException uce) {
			System.err.println("Unable to retrieve Selenium log entries: Cannot call non W3C standard command while in W3C mode");
		} catch (UnreachableBrowserException uce) {
			System.err.println("Unable to retrieve Selenium log entries: Cannot longer reach browser; it may have died already");
		}
	}

	private static void writeLogFile(Logs logs, String type, String testName) {
		Path summaryPath = FileSystems.getDefault().getPath(WebDriverEventListener.TESTDROPIN_LOGFILES_DIR,
				AbstractWebDriverEventListener.convertTestname2FileName(testName) + "-" + type + "-log.txt");
		StringBuilder sb = new StringBuilder();
		
		final LogEntries logEntries = logs.get(type);
		int numOfLogEntries = logEntries.getAll().size();
		if (numOfLogEntries == 0) {
			System.err.println("No log entries found for Selenium log of type '" + type + "'");
			return;
		}
		for (LogEntry logEntry : logEntries) {
			sb.append(logEntry.toString()).append(System.lineSeparator());
		}
		try {
			Files.write(summaryPath, sb.toString().getBytes(), java.nio.file.StandardOpenOption.CREATE);
			if (numOfLogEntries == 1)
				System.out.println("Successfuly wrote " + numOfLogEntries + " log entry to disk for Selenium log of type '" + type + "'");
			else
				System.out.println("Successfuly wrote " + numOfLogEntries + " log entries to disk for Selenium log of type '" + type + "'");
		} catch (IOException e) {
			System.err.println("Error while writing Selenium log file " + summaryPath.toFile().getAbsolutePath());
			e.printStackTrace();
		}
	}
}
