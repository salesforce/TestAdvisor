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
import com.salesforce.selenium.support.event.EventFiringWebDriver;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpClient.Factory;
import org.openqa.selenium.remote.internal.OkHttpClient;
import org.testng.Assert;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Factory for creating an {@link EventFiringWebDriver} which works in Salesforce Central QE environment.
 */
public class WebDriverFactory {
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

		if(platform == Platform.desktop) {
			caps.setCapability("browserName", env.getBrowser().toString());
			caps.setCapability("version", env.getBrowserVersion());
			caps.setCapability("screenResolution", env.getBrowserScreenResolution());
		}

		caps.setCapability("timeZone", env.getOsTimeZone());
		caps.setCapability("name", testName);

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
					// restrict the execution of test if all the allowed VM's are utilised.
					if (utilizedVMs > allowedVMs) {
						// exception will be thrown to prevent test to execute.
						throw new RuntimeException("All allowed (" + utilizedVMs + " VM's are already utilized. Please try to execute local tests on saucelabs after sometime.");
					}
				}

			printMsg("Connecting to saucelabs.");
			String URL = "https://" + sauceName + ":" + sauceKey + "@ondemand.saucelabs.com:443/wd/hub";
			caps.setCapability("username",sauceName);
			caps.setCapability("accessKey",sauceKey);

			if (platform == Platform.desktop) {
				// SauceLabs allows to choose the platform to run on
				caps.setCapability("platform", env.getOsPlatform());
				caps.setCapability("commandTimeout", env.getSauceLabCommandTimeout());
				caps.setCapability("idleTimeout", env.getSauceLabIdleTimeout());
				caps.setCapability("extendedDebugging", true);
				caps.setCapability("maxDuration",env.getSauceLabMaxDuration());
				if (browser == Browser.chrome) {
					caps.setCapability(ChromeOptions.CAPABILITY, disableShowNotificationsForChrome());
				} else if (browser == Browser.firefox) {
					disableShowNotificationsForFirefox().merge(caps);
				}
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
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setHttpProxy(proxyUrl).setSslProxy(proxyUrl);
			proxy.setFtpProxy(proxyUrl).setSocksProxy(proxyUrl);
			caps.setCapability(CapabilityType.PROXY, proxy);
			// no BREAK here by design!
		case local:
			String driverVersion = System.getProperty("driver.version");;
			if (browser == Browser.chrome) {
				if(driverVersion==null) {
					WebDriverManager.chromedriver().setup();
				}
				else{
					WebDriverManager.chromedriver().version(driverVersion).setup();
				}
				driver = new ChromeDriver(disableShowNotificationsForChrome().merge(caps));
			} else if (browser == Browser.firefox) {
				if(driverVersion == null) {
					WebDriverManager.firefoxdriver().setup();
				}
				else{
					WebDriverManager.firefoxdriver().version(driverVersion).setup();
				}
				driver = new FirefoxDriver(disableShowNotificationsForFirefox().merge(caps));
			}
			driver.manage().window().setSize(env.getBrowserScreenResolutionAsDimension());
			break;
		case docker:
			printMsg("Connecting to a localhost docker selenium grid.");
			try{

				String buildName = System.getProperty("build");
				ChromeOptions options = new ChromeOptions();
				// commented as we are not using zalenium at the moment
				// options.setCapability("build",buildName);
				caps.setCapability("enableVNC",true);
				caps.setCapability("enableVideo",true);
				caps.setCapability("name",testName);
				caps.setCapability("videoName",testName + ".mp4");

				org.openqa.selenium.Proxy publicProxy = new org.openqa.selenium.Proxy();
				publicProxy.setSslProxy("public0-proxy1-0-xrd.data.sfdc.net:8080");
				publicProxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
				options.setCapability("proxy", publicProxy);
				caps.setCapability(ChromeOptions.CAPABILITY,options);
				String hub = System.getProperty("HUB_HOST", "10.233.160.157");
				String port = System.getProperty("HUB_PORT", "4444");
				driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub",hub,port)), caps);
			// To verify if proxy capability is setting up
			/*	Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
				System.out.println("caps= " + cap.getCapability("proxy").toString());*/
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
			if ("yes".equalsIgnoreCase(System.getProperty(ShadowJSPathGenerator.RECORD_SHADOWJSPATH, ""))) {
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
	/*	commeneted it out as Zalenium is not being used at this moment - test execution is on docker
		else if (env.getContextType() == TestContext.Type.docker) {
			String testResult = String.valueOf(hasPassed);
			System.out.println("Zalenium test result: " + hasPassed);
			// marking test pass/fail as per test result.
			Cookie cookie = new Cookie("zaleniumTestPassed", testResult);
			driver.manage().addCookie(cookie);
		}*/
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
}
