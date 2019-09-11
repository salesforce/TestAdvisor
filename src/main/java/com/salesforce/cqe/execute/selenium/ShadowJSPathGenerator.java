package com.salesforce.cqe.execute.selenium;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;

/**
 * Checks if a given WebElement object is inside a shadowRoot. If yes, it will
 * be added to a list which gets printed to disk at the end of the test run.
 * This list shows the locator used during the test execution and the JavaScript
 * command potentially required once the shadowDOM rules get tightened.
 * <p>
 * As this is a rather resource hungry activity, it is only active if the System
 * property {@code record.shadowjspath} is set when starting the test execution.
 * <p>
 * Note: This class loads a sub-set of the JavaScript code made available at
 * https://git.soma.salesforce.com/cqe/lwc-shadowpath/blob/master/devtools.js
 * and executes it for every element retrieved by findElement() calls.
 * 
 * @author gneumann
 */
public class ShadowJSPathGenerator extends AbstractWebDriverEventListener {
	/**
	 * Set this System property {@value} to any value to enable recording
	 * of ShadowRoot JS Path information to file.
	 */
	public static final String RECORD_SHADOWJSPATH = "record.shadowjspath";

	private static final String DEVTOOLS_JS_FILE = "/devtools.js";

	private final JavascriptExecutor jsExecutor;
	private final String shadowJSPathGeneratorScript;
	private final Map<String, String> dictionary;
	private final String fileName;
	
	/**
	 * Loads the JavaScript code which retrieves the proper command.
	 * 
	 * @param driver current WebDriver instance
	 * @param testName name of the currently executed test
	 */
	public ShadowJSPathGenerator(WebDriver driver, String testName) {
		this.jsExecutor = (JavascriptExecutor) driver;
		this.dictionary = new HashMap<>();
		this.fileName = "target/" + convertTestname2FileName(testName) + "-lwc-dict.txt";
		String tempJSCode = "";
		try {
			tempJSCode = IOUtils.toString(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEVTOOLS_JS_FILE))));
			System.out.println(">>> LWC Locators Dictionary generator successfully loaded");
		} catch (Exception e) {
			System.err.println("Problem reading LWC Locators Dictionary generator script " + DEVTOOLS_JS_FILE);
			e.printStackTrace();
		}
		this.shadowJSPathGeneratorScript = tempJSCode;
	}

	@Override
	public void closeListener() {
		FileWriter fileWriter = null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("Number of locators inside shadowRoot found: ").append(dictionary.size()).append("\n\n");
		for (String locator : dictionary.keySet()) {
			buffer.append(locator).append(" >>> ").append(dictionary.get(locator)).append("\n");
		}

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(buffer.toString());
			System.out.println("Done writing LWC Locators Dictionary to " + fileName);
		} catch (IOException e) {
			System.err.println("Error while writing LWC Locators Dictionary to " + fileName);
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex) {
				System.err.println("Error while trying to close file writer to " + fileName);
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void afterFindElementByElement(Step step, WebElement returnedElement, By by, WebElement element) {
		runScript(returnedElement, by);
	}

	@Override
	public void afterFindElementByWebDriver(Step step, WebElement returnedElement, By by) {
		runScript(returnedElement, by);
	}

	private void runScript(WebElement returnedElement, By by) {
		if (returnedElement != null && !shadowJSPathGeneratorScript.isEmpty()) {
			String jsPath = jsExecutor.executeScript(shadowJSPathGeneratorScript, returnedElement).toString();
			if (jsPath != null && !jsPath.isEmpty()) {
				// almost all the time we get a path returned
				if (jsPath.contains("shadowRoot"))
					// only if path contains "shadowRoot", it's a locator we want to add to the dictionary
					dictionary.put(by.toString(), jsPath);
			}
		}
	}
}
