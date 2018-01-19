/**
 * 
 */
package com.salesforce.cqe.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Test Context information to be used for executing tests.
 * <p>
 * Source SB ID - Org id of source sandbox
 * Source SB Name - Name of source sandbox
 * Target SB ID - Org id of target sandbox
 * Target SB Name - Name of target sandbox
 * Release of copied SB - 212/214/...; if revved up it indicates we have the first run after the release.
 * Test repository name - Can be used in CI script to get test project code
 * Test repository's active branch name - Usually "master" but could be different. CI could use the branch name for pulling test code. 
 * Date of last SB copy - If there is a new SB copy this may have influence on test result comparison. 
 * Date of last test project drop - Most of the time the latest change will come from CQE. If there is a new drop this may have influence on test result comparison. 
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

	// generic test project information
	private String sourceOrgId;
	private String sourceOrgName;
	private String targetOrgId;
	private String targetOrgName;
	private String releaseOfTargetOrg;
	private String testRepoName;
	private String testRepoBranch = "master";
	private String dateOfLastSBCopy;
	private String dateOfLastTestRepoDrop;
	
	// context type can be overridden by using system property "testcontext.remote"
	private Type contextType = Type.local;

	// default SauceLabs account is Tao's
	private String sauceLab_userName = "tstarbow";
	private String sauceLab_accessKey = "cf312d48-6250-40bf-82ad-a70991bdec72";

	// browser name can be overridden by using system property "testcontext.browser"
	private Browser browser = Browser.firefox;
	private String browser_version = "45.0";
	private String browser_screenResolution = "1920x1200";
	private String browser_implicitTimeout = "45";

	private String os_platform = "Windows 10";
	private String os_timeZone = "Los Angeles";

	// proxy can be overridden by using system property "testcontext.proxy_url"
	// for no proxy settings set "testcontext.proxy_url" to "none".
	private String os_proxy_url = "public0-proxy1-0-prd.data.sfdc.net:8080";

	// cached values of different types
	@JsonIgnore
	private int implicitTimeout = -1;
	@JsonIgnore
	private Dimension screenSize = null;

	public String getSourceOrgId() {
		return sourceOrgId;
	}
	
	public String getSourceOrgName() {
		return sourceOrgName;
	}
	
	public String getTargetOrgId() {
		return targetOrgId;
	}
	
	public String getTargetOrgName() {
		return targetOrgName;
	}
	
	public String getReleaseOfTargetOrg() {
		return releaseOfTargetOrg;
	}
	
	public String getTestRepoName() {
		return testRepoName;
	}
	
	public String getTestRepoBranch() {
		return testRepoBranch;
	}
	
	public String getDateOfLastSBCopy() {
		return dateOfLastSBCopy;
	}

	public String getDateOfLastTestRepoDrop() {
		return dateOfLastTestRepoDrop;
	}

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

	/**
	 * Gets the desired screen size as string value. The format of the value has already been validated.
	 * @return proper screen size string value
	 */
	public String getBrowser_screenResolution() {
		if (screenSize != null) {
			// we have already checked the value
			return browser_screenResolution;
		}

		if (browser_screenResolution != null && !browser_screenResolution.isEmpty()) {
			String[] splittedRes = browser_screenResolution.split("x");
			if (splittedRes.length != 2) {
				throw new IllegalArgumentException("Can't parse browser_screenResolution '" + browser_screenResolution + "'! It has to be of format [width]'x'[height] e.g. '1920x1200'");
			}
			try {
				screenSize = new Dimension(Integer.parseInt(splittedRes[0]), Integer.parseInt(splittedRes[1]));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Can't parse browser_screenResolution '" + browser_screenResolution + "'! It has to be of format [width]'x'[height] e.g. '1920x1200'");
			}
		}
		return browser_screenResolution;
	}
	
	public Dimension getScreenResolutionAsDimension() {
		return screenSize;
	}
	
	/**
	 * Gets the implicit timeout value as string. The value has already been validated to be an integer.
	 * @return proper seconds string value
	 */
	public String getBrowser_implicitTimeout() {
		if (implicitTimeout != -1) {
			// we have already checked the value
			return browser_implicitTimeout;
		}

		if (browser_implicitTimeout != null && !browser_implicitTimeout.isEmpty()) {
			try {
				implicitTimeout = Integer.parseInt(browser_implicitTimeout);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Can't parse browser_implicitTimeout '" + browser_implicitTimeout + "'! It has to be of format [secs] e.g. '45'");
			}
		}
		return browser_implicitTimeout;
	}

	public int getImplicitTimeout() {
		return implicitTimeout;
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
			if ("none".equalsIgnoreCase(proxy)) {
				os_proxy_url = "";
			}
		}
		return os_proxy_url;
	}
}
