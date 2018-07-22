/* 
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license. 
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.cqe.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.stream.MalformedJsonException;

// Class generated using http://www.jsonschema2pojo.org/

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customer",
    "productionOrgId",
    "orgs",
    "selenium"
})
public class TestContext {
	public static final String JSON_FILENAME = "testcontext.json";

	public enum Type {
		local, saucelabs, privatecloud;
	}
	public enum Browser {
		chrome, ie, firefox, safari
	}

	@JsonProperty("customer")
    private String customer;
    @JsonProperty("productionOrgId")
    private String productionOrgId;
    @JsonProperty("orgs")
    private Orgs orgs;
    @JsonProperty("selenium")
    private SeleniumEnvs seleniumEnvs;

	private TestContext() {
		; // prohibit default constructor
	}

	/**
	 * Reads JSON file "testcontext.json" from test project's root directory.
	 * @return test context
	 * @throws MalformedJsonException if .json file is not readable.
	 */
	public static TestContext getContext() throws MalformedJsonException {
		TestContext testContext = JsonHelper.toObject(TestContext.JSON_FILENAME, TestContext.class);
		if (testContext == null) {
			throw new MalformedJsonException("Could not get test context information from JSON file " + TestContext.JSON_FILENAME);
		}
		return testContext;
	}

	@JsonProperty("customer")
    public String getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @JsonProperty("productionOrgId")
    public String getProductionOrgId() {
        return productionOrgId;
    }

    @JsonProperty("productionOrgId")
    public void setProductionOrgId(String productionOrgId) {
        this.productionOrgId = productionOrgId;
    }

    @JsonProperty("orgs")
    public Orgs getOrgs() {
        return orgs;
    }

    @JsonProperty("orgs")
    public void setOrgs(Orgs orgs) {
        this.orgs = orgs;
    }

    @JsonProperty("selenium")
    public SeleniumEnvs getSeleniumEnvs() {
        return seleniumEnvs;
    }

    @JsonProperty("selenium")
    public void setSeleniumEnvs(SeleniumEnvs selenium) {
        this.seleniumEnvs = selenium;
    }

	@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "source",
        "clone"
    })
    public static class Orgs {
        @JsonProperty("source")
        private Sandbox source;
        @JsonProperty("clone")
        private Sandbox clone;

        @JsonProperty("source")
        public Sandbox getSource() {
            return source;
        }

        @JsonProperty("source")
        public void setSource(Sandbox source) {
            this.source = source;
        }

        @JsonProperty("clone")
        public Sandbox getClone() {
            return clone;
        }

        @JsonProperty("clone")
        public void setClone(Sandbox clone) {
            this.clone = clone;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "sandboxOrgId",
        "sandboxOrgName",
        "sandboxPod"
    })
    public static class Sandbox {
        @JsonProperty("sandboxOrgId")
        private String sandboxOrgId;
        @JsonProperty("sandboxOrgName")
        private String sandboxOrgName;
        @JsonProperty("sandboxPod")
        private String sandboxPod;

        @JsonProperty("sandboxOrgId")
        public String getSandboxOrgId() {
            return sandboxOrgId;
        }

        @JsonProperty("sandboxOrgId")
        public void setSandboxOrgId(String sandboxOrgId) {
            this.sandboxOrgId = sandboxOrgId;
        }

        @JsonProperty("sandboxOrgName")
        public String getSandboxOrgName() {
            return sandboxOrgName;
        }

        @JsonProperty("sandboxOrgName")
        public void setSandboxOrgName(String sandboxOrgName) {
            this.sandboxOrgName = sandboxOrgName;
        }

        @JsonProperty("sandboxPod")
        public String getSandboxPod() {
            return sandboxPod;
        }

        @JsonProperty("sandboxPod")
        public void setSandboxPod(String sandboxPod) {
            this.sandboxPod = sandboxPod;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "jenkins",
        "local"
    })
    public static class SeleniumEnvs {
        @JsonProperty("jenkins")
        private Env jenkins;
        @JsonProperty("local")
        private Env local;

        @JsonProperty("jenkins")
        public Env getJenkins() {
            return jenkins;
        }

        @JsonProperty("jenkins")
        public void setJenkins(Env jenkins) {
            this.jenkins = jenkins;
        }

        @JsonProperty("local")
        public Env getLocal() {
            return local;
        }

        @JsonProperty("local")
        public void setLocal(Env local) {
            this.local = local;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "contextType",
        "sauceLab_userName",
        "sauceLab_accessKey",
        "browser",
        "browser_version",
        "browser_screenResolution",
        "browser_implicitTimeout",
        "os_platform",
        "os_timeZone",
        "os_proxy_url"
    })
    public static class Env {
    	// context type can be overridden by using system property "testcontext.remote"
        @JsonProperty("contextType")
        private Type contextType = Type.local;

        // default SauceLabs account is Tao's
        @JsonProperty("sauceLab_userName")
        private String sauceLabUserName = "tstarbow";
        @JsonProperty("sauceLab_accessKey")
        private String sauceLabAccessKey = "cf312d48-6250-40bf-82ad-a70991bdec72";

        // browser name can be overridden by using system property "testcontext.browser"
        @JsonProperty("browser")
        private Browser browser = Browser.firefox;
        @JsonProperty("browser_version")
        private String browserVersion = "45.0";
        @JsonProperty("browser_screenResolution")
        private String browserScreenResolution = "1920x1200";
        @JsonProperty("browser_implicitTimeout")
        private long browserImplicitTimeout = 45L;

        @JsonProperty("os_platform")
        private String osPlatform = "Windows 10";
        @JsonProperty("os_timeZone")
        private String osTimeZone = "Los Angeles";

    	// proxy can be overridden by using system property "testcontext.proxy_url"
    	// for no proxy settings set "testcontext.proxy_url" to "none".
        @JsonProperty("os_proxy_url")
        private String osProxyUrl = "public0-proxy1-0-prd.data.sfdc.net:8080";

        @JsonProperty("contextType")
        public void setContextType(Type type) {
			// is type overridden?
    		String typeName = System.getProperty("testcontext.remote");
    		this.contextType = (StringUtils.isNotBlank(typeName)) ? Type.valueOf(typeName) : type;
    	}

        @JsonProperty("contextType")
        public Type getContextType() {
    		return this.contextType;
    	}

        @JsonProperty("sauceLab_userName")
        public String getSauceLabUserName() {
            return sauceLabUserName;
        }

        /*
         * Accept value as-is.
         */
        @JsonProperty("sauceLab_userName")
        public void setSauceLabUserName(String sauceLabUserName) {
            this.sauceLabUserName = sauceLabUserName;
        }

        @JsonProperty("sauceLab_accessKey")
        public String getSauceLabAccessKey() {
            return sauceLabAccessKey;
        }

        /*
         * Accept value as-is.
         */
        @JsonProperty("sauceLab_accessKey")
        public void setSauceLabAccessKey(String sauceLabAccessKey) {
            this.sauceLabAccessKey = sauceLabAccessKey;
        }

        @JsonProperty("browser")
        public Browser getBrowser() {
        	return this.browser;
        }

        @JsonProperty("browser")
        public void setBrowser(Browser browser) {
			// is browser overridden?
    		String browserName = System.getProperty("testcontext.browser");
    		this.browser = (StringUtils.isNotBlank(browserName)) ? Browser.valueOf(browserName.toLowerCase()) : browser;
        }

        @JsonProperty("browser_version")
        public String getBrowserVersion() {
            return browserVersion;
        }

        /*
         * Accept value as-is.
         */
        @JsonProperty("browser_version")
        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        @JsonProperty("browser_screenResolution")
        public String getBrowserScreenResolution() {
            return browserScreenResolution;
        }

    	@JsonIgnore
        public Dimension getBrowserScreenResolutionAsDimension() {
        	Dimension browserScreenResolutionDimension = null;
        	if (StringUtils.isNotEmpty(browserScreenResolution)) {
				// splitting is OK; already validated in the setter
    			String[] splittedRes = browserScreenResolution.split("x");
    			try {
    				// creating object is OK; already validated in the setter
    				browserScreenResolutionDimension = new Dimension(Integer.parseInt(splittedRes[0]), Integer.parseInt(splittedRes[1]));
    			} catch (NumberFormatException e) {
    				;
    			}
    		}
            return browserScreenResolutionDimension;
        }

        @JsonProperty("browser_screenResolution")
        public void setBrowserScreenResolution(String browserScreenResolution) {
        	if (StringUtils.isNotEmpty(browserScreenResolution)) {
    			String[] splittedRes = browserScreenResolution.split("x");
    			if (splittedRes.length != 2) {
    				throw new IllegalArgumentException("Can't parse browser_screenResolution '" + browserScreenResolution + "'! It has to be of format [width]'x'[height] e.g. '1920x1200'");
    			}
    			try {
    				// try to create a new Dimension object for validation
    				new Dimension(Integer.parseInt(splittedRes[0]), Integer.parseInt(splittedRes[1]));
    				// now we know all is OK with the screen resolution setting
                	this.browserScreenResolution = browserScreenResolution;
    			} catch (NumberFormatException e) {
    				throw new IllegalArgumentException("Can't parse browser_screenResolution '" + browserScreenResolution + "'! It has to be of format [width]'x'[height] e.g. '1920x1200'");
    			}
    		}
        }
        
        @JsonProperty("browser_implicitTimeout")
        public long getBrowserImplicitTimeout() {
            return browserImplicitTimeout;
        }

        @JsonProperty("browser_implicitTimeout")
        public void setBrowserImplicitTimeout(long browserImplicitTimeout) {
    		this.browserImplicitTimeout = browserImplicitTimeout;
        }

        @JsonProperty("os_platform")
        public String getOsPlatform() {
            return osPlatform;
        }

        /*
         * Accept value as-is.
         */
        @JsonProperty("os_platform")
        public void setOsPlatform(String osPlatform) {
            this.osPlatform = osPlatform;
        }

        @JsonProperty("os_timeZone")
        public String getOsTimeZone() {
            return osTimeZone;
        }

        /*
         * Accept value as-is.
         */
        @JsonProperty("os_timeZone")
        public void setOsTimeZone(String osTimeZone) {
            this.osTimeZone = osTimeZone;
        }

        @JsonProperty("os_proxy_url")
        public String getOsProxyUrl() {
			// is proxy overridden?
    		String proxy = System.getProperty("testcontext.proxy_url");
    		if (StringUtils.isNotBlank(proxy)) {
    			osProxyUrl = proxy;
    			if ("none".equalsIgnoreCase(proxy)) {
    				osProxyUrl = "";
    			}
    		}
    		return osProxyUrl;
        }

        @JsonProperty("os_proxy_url")
        public void setOsProxyUrl(String osProxyUrl) {
			// is proxy overridden?
    		String proxy = System.getProperty("testcontext.proxy_url");
    		if (StringUtils.isNotBlank(proxy)) {
    			osProxyUrl = proxy;
    			if ("none".equalsIgnoreCase(proxy)) {
    				osProxyUrl = "";
    			}
    		} // otherwise keep default value
            this.osProxyUrl = osProxyUrl;
        }
    }
}
