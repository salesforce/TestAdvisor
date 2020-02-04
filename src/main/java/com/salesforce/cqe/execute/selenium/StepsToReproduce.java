/**
 * 
 */
package com.salesforce.cqe.execute.selenium;

import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;
import com.salesforce.selenium.support.event.Step;
import com.salesforce.selenium.support.event.Step.Cmd;

public class StepsToReproduce extends AbstractWebDriverEventListener {
	private int stepCounter = 1;
	private String fileName = null;
	private StringBuffer buffer = new StringBuffer();
	private String lastResultString = null;

	public StepsToReproduce(String testName) {
		this.fileName = "target/" + convertTestname2FileName(testName) + ".txt";
	}

	@Override
	public void closeListener() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write(buffer.toString());
			System.out.println("Done writing steps to reproduce to " + fileName);
		} catch (IOException e) {
			System.err.println("Error while writing steps to reproduce to " + fileName);
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

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void afterClose(Step step) {
		String result = "Step " + stepCounter + ": close tab";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGet(Step step, String url) {
		String result = "Step " + stepCounter + ": open page " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterQuit(Step step) {
		String result = "Step " + stepCounter + ": close browser";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterBack(Step step) {
		String result = "Step " + stepCounter + ": press Back button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterForward(Step step) {
		String result = "Step " + stepCounter + ": press Forward button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterRefresh(Step step) {
		String result = "Step " + stepCounter + ": press Refresh button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterTo(Step step, String url) {
		String result = "Step " + stepCounter + ": in address bar got to URL " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterAlert(Step step, Alert alert) {
		String result = "Step " + stepCounter + ": switch to alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterClick(Step step, WebElement element) {
		String result = "Step " + stepCounter + ": click on element " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterClear(Step step, WebElement element) {
		String result = "Step " + stepCounter + ": clear input field " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGetAttribute(Step step, String value, String name, WebElement element) {
		String result = "Step " + stepCounter + ": check attribute " + name + " of element " + Step.getLocatorFromWebElement(element) + " has value " + value;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGetCssValue(Step step, String propertyName, String value, WebElement element) {
		String result = "Step " + stepCounter + ": check CSS attribute " + propertyName + " of element " + Step.getLocatorFromWebElement(element) + " has value " + value;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGetTagName(Step step, String tagName, WebElement element) {
		String result = "Step " + stepCounter + ": check tag name of element " + Step.getLocatorFromWebElement(element) + " is value " + tagName;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGetText(Step step, String text, WebElement element) {
		String result = "Step " + stepCounter + ": check text under element " + Step.getLocatorFromWebElement(element) + " is '" + text + "'";
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterIsDisplayed(Step step, boolean isDisplayed, WebElement element) {
		String visible = (isDisplayed) ? "visible" : "not visible";
		String result = "Step " + stepCounter + ": check element " + Step.getLocatorFromWebElement(element) + " is " + visible;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterIsEnabled(Step step, boolean isEnabled, WebElement element) {
		String enabled = (isEnabled) ? "enabled" : "not enabled";
		String result = "Step " + stepCounter + ": check element " + Step.getLocatorFromWebElement(element) + " is " + enabled;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterIsSelected(Step step, boolean isSelected, WebElement element) {
		String selected = (isSelected) ? "selected" : "not selected";
		String result = "Step " + stepCounter + ": check element " + Step.getLocatorFromWebElement(element) + " is " + selected;
		if (result.equals(lastResultString)) {
			// don't repeat repeated verification steps
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend) {
		String result = "Step " + stepCounter + ": enter text '" + step.getParam2() + "' into input field " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSubmit(Step step, WebElement element) {
		String result = "Step " + stepCounter + ": click on Submit button " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void onException(Step step, Cmd cmd, Throwable issue) {
		String result = "Step " + stepCounter + ": command " + step.getCmd().getShortCmdString() + " failed with error " + issue.getMessage();
		if (result.equals(lastResultString)) {
			// don't repeat repeated failures
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}
}
