package com.salesforce.cqe.execute.selenium;

import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;

/**
 * Collect all locators used by test, count them and finally write to disk.
 * @author gneumann
 */
public class LogLocators extends AbstractWebDriverEventListener {
	private int counter = 0;
	private String fileName = null;
	private StringBuffer buffer = new StringBuffer();

	public LogLocators(String testName) {
		this.fileName = "target/" + convertTestname2FileName(testName) + "-locators.txt";
	}

	@Override
	public void beforeFindElementByWebDriver(Step step, By by) {
		buffer.append(by.toString() + System.lineSeparator());
		counter++;
	}

	@Override
	public void beforeFindElementsByWebDriver(Step step, By by) {
		buffer.append(by.toString() + System.lineSeparator());
		counter++;
	}

	@Override
	public void beforeFindElementByElement(Step step, By by, WebElement element) {
		buffer.append(by.toString() + System.lineSeparator());
		counter++;
	}

	@Override
	public void beforeFindElementsByElement(Step step, By by, WebElement element) {
		buffer.append(by.toString() + System.lineSeparator());
		counter++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.selenium.support.event.WebDriverEventListener#closeListener()
	 */
	@Override
	public void closeListener() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(counter + System.lineSeparator() + buffer.toString());
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
}
