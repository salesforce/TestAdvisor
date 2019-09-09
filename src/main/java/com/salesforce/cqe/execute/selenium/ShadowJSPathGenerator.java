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

public class ShadowJSPathGenerator extends AbstractWebDriverEventListener {
	private static final String DEVTOOLS_JS_FILE = "/devtools.js";
	private final JavascriptExecutor jsExecutor;
	// taken from https://git.soma.salesforce.com/cqe/lwc-shadowpath/blob/master/devtools.js
	private final String shadowJSPathGeneratorScript;

	private final Map<String, String> dictionary;
	private final String fileName;
	
	public ShadowJSPathGenerator(WebDriver driver, String testName) {
		this.jsExecutor = (JavascriptExecutor) driver;
		String tempJSCode = "";
		try {
			tempJSCode = IOUtils.toString(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEVTOOLS_JS_FILE))));
			System.out.println(">>> ShadowJSPathGenerator script successfully loaded");
		} catch (Exception e) {
			System.err.println("Problem reading " + DEVTOOLS_JS_FILE);
			e.printStackTrace();
		}
		this.shadowJSPathGeneratorScript = tempJSCode;
		this.dictionary = new HashMap<>();
		this.fileName = "target/" + convertTestname2FileName(testName) + "-lwc-dict.txt";
	}

	@Override
	public void closeListener() {
		FileWriter fileWriter = null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("Number of Locators inside ShadowDOM found: ").append(dictionary.size()).append("\n\n");
		for (String locator : dictionary.keySet()) {
			buffer.append("Locator: ").append(locator).append(" >>> ").append(dictionary.get(locator)).append("\n");
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
				dictionary.put(by.toString(), jsPath);
				printMsg(by.toString(), jsPath);
			}
		}
	}
	
	private static void printMsg(String locator, String script) {
		System.out.printf(">>> Element inside ShadowDOM!%n>>> Locator: %s%n>>> Script: %s%n", locator, script);
	}
}
