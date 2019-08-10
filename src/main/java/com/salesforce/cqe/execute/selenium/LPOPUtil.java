/**
 * 
 */
package com.salesforce.cqe.execute.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.salesforce.lpop.webutils.impl.seti.ShadowRootWebElement;

/**
 * Methods copy-pasted from BaseWebDriverUtil in core and augmented with code in Quip:
 * "WebDriver + Shadow DOM traversal code samples" (https://salesforce.quip.com/sXNZAZtvgCZW)
 */
public class LPOPUtil {
	public final static WebElement findElementInShadowRoot(WebDriver driver, WebElement rootElement, By... selectors) {
	    WebElement curr = expandShadowIfExists(driver, rootElement);
	    for (int i = 0; i < selectors.length; i++) {
	        curr = curr.findElement(selectors[i]);
	        if (i != selectors.length -1) {
	            curr = expandShadowIfExists(driver, curr);
	        }
	    }
	    return curr;
	}

	public final static WebElement expandShadowIfExists(WebDriver driver, WebElement sc) {
		if (sc instanceof WebElement && Boolean.TRUE.equals(executeScript(driver, getCheckShadowRootScript(), sc))) {
			return new ShadowRootWebElement(sc);
		}
		return sc;
	}

	private static String getCheckShadowRootScript() {
		StringBuilder scriptBuilder = new StringBuilder("var shadowRoot = arguments[0].shadowRoot;");
		scriptBuilder.append("if (shadowRoot && shadowRoot.nodeType === 11 && !!shadowRoot.host) {");
		scriptBuilder.append("    return true;");
		scriptBuilder.append("}");
		scriptBuilder.append("return false;");
		return scriptBuilder.toString();
	}

	private static Object executeScript(WebDriver driver, String script, Object... parameters) {
		return ((JavascriptExecutor) driver).executeScript(script, parameters);
	}
}
