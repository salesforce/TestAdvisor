/**
 * 
 */
package com.salesforce.cqe.execute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.cqe.execute.selenium.LogLocators;
import com.salesforce.selenium.support.event.Step;

/**
 * @author gneumann
 *
 */
public class LogLocatorsTest {
	private LogLocators logLocators;

	@BeforeMethod
	public void setUp() {
		logLocators = new LogLocators("log");
	}

	@Test
	public void testLogLocators() {
		Step stepBefore = new Step(Step.Type.BeforeAction, 1, Step.Cmd.findElementByWebDriver);
		logLocators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.xpath("b"));
		logLocators.beforeFindElementByElement(stepBefore, By.xpath("c"), null);
		logLocators.beforeFindElementsByElement(stepBefore, By.xpath("d"), null);
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("target/log-locators.txt")));
		} catch (IOException e) {
			Assert.fail("Unable to read output file", e);
		}
		Assert.assertTrue(content.contains("4"));
		Assert.assertTrue(content.contains("By.xpath: a"));
		Assert.assertTrue(content.contains("By.xpath: b"));
		Assert.assertTrue(content.contains("By.xpath: c"));
		Assert.assertTrue(content.contains("By.xpath: d"));
		Assert.assertFalse(content.contains("aBy.xpath:"));
	}

	@AfterMethod
	public void tearDown() {
		logLocators.closeListener();
	}
}
