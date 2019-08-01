/**
 * 
 */
package com.salesforce.cqe.execute.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.salesforce.lpop.webutils.impl.seti.ShadowRootWebElement;

/**
 * Methods copy-pasted from BaseWebDriverUtil in core.
 */
public class LPOPUtil {
	public static Object executeScript(WebDriver driver, String script, Object... parameters) {
		return ((JavascriptExecutor) driver).executeScript(script, parameters);
	}

	public static String getCheckShadowRootScript() {
		StringBuilder scriptBuilder = new StringBuilder("var shadowRoot = arguments[0].shadowRoot;");
		scriptBuilder.append("if (shadowRoot && shadowRoot.nodeType === 11 && !!shadowRoot.host) {");
		scriptBuilder.append("    return true;");
		scriptBuilder.append("}");
		scriptBuilder.append("return false;");
		return scriptBuilder.toString();
	}

	public final static SearchContext expandShadowIfExists(WebDriver driver, SearchContext sc) {
		if (sc instanceof WebElement && Boolean.TRUE.equals(executeScript(driver, getCheckShadowRootScript(), sc))) {
			return new ShadowRootWebElement((WebElement) sc);
		}
		return sc;
	}
}
