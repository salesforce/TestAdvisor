/**
 * 
 */
package com.salesforce.cqe.execute;

import java.io.File;
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
		File summaryFile = new File("target/locators-statistics.csv");
		if (summaryFile.exists())
			summaryFile.delete();

		LogLocators tc1Locators = new LogLocators("testCase1");
		Step stepBefore = new Step(Step.Type.BeforeAction, 1, Step.Cmd.findElementByWebDriver);
		tc1Locators.beforeFindElementsByElement(stepBefore, By.xpath("d"), null);
		tc1Locators.beforeFindElementByElement(stepBefore, By.xpath("c"), null);
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.xpath("b"));
		tc1Locators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.cssSelector("e"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.id("f"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.name("g"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.tagName("h"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.className("i"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.linkText("j"));
		tc1Locators.beforeFindElementsByWebDriver(stepBefore, By.partialLinkText("k"));
		tc1Locators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		tc1Locators.beforeFindElementByWebDriver(stepBefore, By.xpath("a"));
		tc1Locators.closeListener();

		LogLocators tc2Locators = new LogLocators("testCase2");
		tc2Locators.beforeFindElementByWebDriver(stepBefore, By.xpath("a2"));
		tc2Locators.beforeFindElementsByWebDriver(stepBefore, By.cssSelector("e2"));
		tc2Locators.beforeFindElementsByWebDriver(stepBefore, By.id("f2"));
		tc2Locators.closeListener();

		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("target/testCase1-locators.txt")));
		} catch (IOException e) {
			Assert.fail("Unable to read output file", e);
		}
		Assert.assertTrue(content.contains("# of findElement(s) calls:  13"));
		Assert.assertTrue(content.contains("By.xpath: a"));
		Assert.assertTrue(content.contains("By.xpath: b"));
		Assert.assertTrue(content.contains("By.xpath: c"));
		Assert.assertTrue(content.contains("By.xpath: d"));
		Assert.assertTrue(content.contains("By.cssSelector: e"));
		Assert.assertTrue(content.contains("By.id: f"));
		Assert.assertTrue(content.contains("By.name: g"));
		Assert.assertTrue(content.contains("By.tagName: h"));
		Assert.assertTrue(content.contains("By.className: i"));
		Assert.assertTrue(content.contains("By.linkText: j"));
		Assert.assertTrue(content.contains("By.partialLinkText: k"));
		Assert.assertFalse(content.contains("aBy.xpath:"));

		try {
			content = new String(Files.readAllBytes(Paths.get("target/locators-statistics.csv")));
			Assert.assertTrue(content.contains("testCase1,13,4,1"));
			Assert.assertTrue(content.contains("testCase2,3,1,1"));
		} catch (IOException e) {
			Assert.fail("Unable to read output file", e);
		}
	}
}
