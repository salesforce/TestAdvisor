package com.salesforce.cqe.execute.selenium;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
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
	private static final String SUMMARY_FILENAME = "locators-statistics.csv";

	private int totalLocatorUsage = 0;
	private Set<String> classLocators = new LinkedHashSet<>();
	private Set<String> cssLocators = new LinkedHashSet<>();
	private Set<String> idLocators = new LinkedHashSet<>();
	private Set<String> linkLocators = new LinkedHashSet<>();
	private Set<String> nameLocators = new LinkedHashSet<>();
	private Set<String> partialLinkLocators = new LinkedHashSet<>();
	private Set<String> tagLocators = new LinkedHashSet<>();
	private Set<String> xpathLocators = new LinkedHashSet<>();
	private String testName = null;

	public LogLocators(String testName) {
		this.testName = convertTestname2FileName(testName);
		writeSummaryFileHeader();
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
		writeTestCaseLocatorsFile();
		writeSummaryFile();
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

	private void writeTestCaseLocatorsFile() {
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
		
		Path path = FileSystems.getDefault().getPath(TESTDROPIN_LOGFILES_DIR, testName + "-locators.txt");

		try {
			Files.createDirectories(path.getParent());
			Files.write(path, sb.toString().getBytes());
			System.out.println("Done writing list of locators to " + testName + "-locators.txt");
		} catch (IOException e) {
			System.err.println("Error while writing list of locators to " + testName + "-locators.txt");
			e.printStackTrace();
		}
	}

	private void writeSummaryFileHeader() {
		// If summary CSV does not yet exist, write column names to file
		Path path = FileSystems.getDefault().getPath("target", SUMMARY_FILENAME);
		if (!path.toFile().exists()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Testname").append(",");
			sb.append("Total").append(",");
			sb.append("XPath").append(",");
			sb.append("CSS").append(",");
			sb.append("ID").append(",");
			sb.append("Name").append(",");
			sb.append("TagName").append(",");
			sb.append("Class").append(",");
			sb.append("Link").append(",");
			sb.append("PartialLink").append(System.lineSeparator());
			try {
				Files.write(path, sb.toString().getBytes());
			} catch (IOException e) {
				System.err.println("Error while writing locator statistics file " + path.toFile().getAbsolutePath());
				e.printStackTrace();
			}
		}
	}

	private void writeSummaryFile() {
		Path summaryPath = FileSystems.getDefault().getPath(TESTDROPIN_LOGFILES_DIR, SUMMARY_FILENAME);
		StringBuilder sb = new StringBuilder();
		sb.append(testName).append(",");
		sb.append(totalLocatorUsage).append(",");
		sb.append(xpathLocators.size()).append(",");
		sb.append(cssLocators.size()).append(",");
		sb.append(idLocators.size()).append(",");
		sb.append(nameLocators.size()).append(",");
		sb.append(tagLocators.size()).append(",");
		sb.append(classLocators.size()).append(",");
		sb.append(linkLocators.size()).append(",");
		sb.append(partialLinkLocators.size()).append(System.lineSeparator());
		try {
			Files.write(summaryPath, sb.toString().getBytes(), java.nio.file.StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println("Error while writing locator statistics file " + summaryPath.toFile().getAbsolutePath());
			e.printStackTrace();
		}
	}
}
