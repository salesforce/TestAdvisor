/**
 * 
 */
package com.salesforce.cqe.execute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.salesforce.cqe.execute.selenium.LogLocators;
import com.salesforce.selenium.support.event.Step;

/**
 * @author gneumann
 *
 */
public class LogLocatorsTest {
	@Test
	public void testLogLocators() {
		LogLocators logLocators = new LogLocators("log");
		Step stepBefore = new Step(Step.Type.BeforeAction, 1, Step.Cmd.findElementByWebDriver);
		logLocators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.xpath("b"));
		logLocators.beforeFindElementByElement(stepBefore, By.xpath("c"), null);
		logLocators.beforeFindElementsByElement(stepBefore, By.xpath("d"), null);
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.cssSelector("e"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.id("f"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.name("g"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.tagName("h"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.linkText("i"));
		logLocators.beforeFindElementsByWebDriver(stepBefore, By.partialLinkText("j"));
		logLocators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		logLocators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		logLocators.closeListener();

		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("target/log-locators.txt")));
		} catch (IOException e) {
			Assert.fail("Unable to read output file", e);
		}
		Assert.assertTrue(content.contains("# of findElement(s) calls:  12"));
		Assert.assertTrue(content.contains("By.xpath: a"));
		Assert.assertTrue(content.contains("By.xpath: b"));
		Assert.assertTrue(content.contains("By.xpath: c"));
		Assert.assertTrue(content.contains("By.xpath: d"));
		Assert.assertTrue(content.contains("By.cssSelector: e"));
		Assert.assertTrue(content.contains("By.id: f"));
		Assert.assertTrue(content.contains("By.name: g"));
		Assert.assertTrue(content.contains("By.tagName: h"));
		Assert.assertTrue(content.contains("By.linkText: i"));
		Assert.assertTrue(content.contains("By.partialLinkText: j"));
		Assert.assertFalse(content.contains("aBy.xpath:"));
	}
}
