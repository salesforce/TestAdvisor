package com.salesforce.cqe.execute.selenium;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;

/**
 * Collect all locators used by test, count them and finally write to disk.
 * @author gneumann
 */
public class LogLocators extends AbstractWebDriverEventListener {
	private int totalLocatorUsage = 0;
	private Set<String> classLocators = new HashSet<>();
	private Set<String> cssLocators = new HashSet<>();
	private Set<String> idLocators = new HashSet<>();
	private Set<String> linkLocators = new HashSet<>();
	private Set<String> nameLocators = new HashSet<>();
	private Set<String> partialLinkLocators = new HashSet<>();
	private Set<String> tagLocators = new HashSet<>();
	private Set<String> xpathLocators = new HashSet<>();
	private String fileName = null;

	public LogLocators(String testName) {
		this.fileName = "target/" + convertTestname2FileName(testName) + "-locators.txt";
	}

	@Override
	public void beforeFindElementByWebDriver(Step step, By by) {
		updateSets(by);
	}

	@Override
	public void beforeFindElementsByWebDriver(Step step, By by) {
		updateSets(by);
	}

	@Override
	public void beforeFindElementByElement(Step step, By by, WebElement element) {
		updateSets(by);
	}

	@Override
	public void beforeFindElementsByElement(Step step, By by, WebElement element) {
		updateSets(by);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.selenium.support.event.WebDriverEventListener#closeListener()
	 */
	@Override
	public void closeListener() {
		StringBuilder sb = new StringBuilder();
		sb.append("# of findElement(s) calls:  ").append(totalLocatorUsage).append(System.lineSeparator());
		sb.append("# of unique XPath locators: ").append(xpathLocators.size()).append(System.lineSeparator());
		sb.append("# of unique CSS locators:   ").append(cssLocators.size()).append(System.lineSeparator());
		sb.append("# of unique ID locators:    ").append(idLocators.size()).append(System.lineSeparator());
		if (nameLocators.size() > 0)
			sb.append("# of unique Name locators:  ").append(nameLocators.size()).append(System.lineSeparator());
		if (tagLocators.size() > 0)
			sb.append("# of unique TagName locs:   ").append(tagLocators.size()).append(System.lineSeparator());
		if (classLocators.size() > 0)
			sb.append("# of unique Class locators: ").append(classLocators.size()).append(System.lineSeparator());
		if (linkLocators.size() > 0)
			sb.append("# of unique Link locators:  ").append(linkLocators.size()).append(System.lineSeparator());
		if (partialLinkLocators.size() > 0)
			sb.append("# of uniq PartialLink locs: ").append(partialLinkLocators.size()).append(System.lineSeparator());

		for (String s : xpathLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : cssLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : idLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : nameLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : tagLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : classLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : linkLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		for (String s : partialLinkLocators) {
			sb.append(s).append(System.lineSeparator());
		}
		
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(sb.toString());
			System.out.println("Done writing list of locators to " + fileName);
		} catch (IOException e) {
			System.err.println("Error while writing list of locators to " + fileName);
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
	
	private void updateSets(By by) {
		totalLocatorUsage++;
		final String byLocator = by.toString();
		final String byType = by.toString().substring(3, 5);
		if ("xp".equals(byType))
			xpathLocators.add(byLocator);
		else if ("cs".equals(byType))
			cssLocators.add(byLocator);
		else if ("id".equals(byType))
			idLocators.add(byLocator);
		else if ("na".equals(byType))
			nameLocators.add(byLocator);
		else if ("ta".equals(byType))
			tagLocators.add(byLocator);
		else if ("cl".equals(byType))
			classLocators.add(byLocator);
		else if ("li".equals(byType))
			linkLocators.add(byLocator);
		else if ("pa".equals(byType))
			partialLinkLocators.add(byLocator);
	}
}
