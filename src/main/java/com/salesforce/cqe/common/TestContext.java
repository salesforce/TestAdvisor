/**
 * 
 */
package com.salesforce.cqe.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Test Context information to be used for executing tests.
 * <p>
 * Objects of this class can be instantiated by de-serializing a JSON file in the test project's root directory.
 * @author gneumann
 */
public class TestContext {
	public static final String JSON_FILENAME = "testcontext.json";

	public enum Type {
		local, saucelabs, privatecloud;
	}
	public enum Browser {
		chrome, ie, firefox, safari
	}

	// context type can be overridden by using system property "testcontext.remote"
	private Type contextType = Type.local;

	// default SauceLabs account is Tao's
	private String sauceLab_userName = "tstarbow";
	private String sauceLab_accessKey = "cf312d48-6250-40bf-82ad-a70991bdec72";

	// browser name can be overridden by using system property "testcontext.browser"
	private Browser browser = Browser.firefox;
	private String browser_version = "45.0";
	private String browser_screenResolution = "1920x1200";

	private String os_platform = "Windows 10";
	private String os_timeZone = "Los Angeles";

	// proxy can be overridden by using system property "testcontext.proxy_url"
	private String os_proxy_url = "public0-proxy1-0-prd.data.sfdc.net:8080";

	public Type getContextType() {
		String typeName = System.getProperty("testcontext.remote");
		if (StringUtils.isNotBlank(typeName)) {
			contextType = Type.valueOf(typeName);
		}
		return contextType;
	}
	public String getSauceLab_userName() {
		return sauceLab_userName;
	}
	public String getSauceLab_accessKey() {
		return sauceLab_accessKey;
	}
	public Browser getBrowser() {
		String name = System.getProperty("testcontext.browser");
		if (StringUtils.isNotBlank(name)) {
			browser = Browser.valueOf(name.toLowerCase());
		}
		return browser;
	}
	public String getBrowser_version() {
		return browser_version;
	}
	public String getBrowser_screenResolution() {
		return browser_screenResolution;
	}
	public String getOs_platform() {
		return os_platform;
	}
	public String getOs_timeZone() {
		return os_timeZone;
	}
	public String getOs_proxy_url() {
		String proxy = System.getProperty("testcontext.proxy_url");
		if (StringUtils.isNotBlank(proxy)) {
			os_proxy_url = proxy;
		}
		return os_proxy_url;
	}
}
