/**
 * 
 */
package com.salesforce.cqe.driver.listener;


import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;

import com.salesforce.cqe.driver.listener.Step.Cmd;

/**
 * Tracks the individual actions of the test.
 * 
 * @author gneumann
 */
public class StepsToReproduce extends AbstractEventListener {
	private int stepCounter = 1;
	private StringBuffer buffer = new StringBuffer();
	private String lastResultString = null;

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void afterClose(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": close tab";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGet(Step step, String url) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": open page " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterQuit(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": close browser";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterBack(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": press Back button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterForward(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": press Forward button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterRefresh(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": press Refresh button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterTo(Step step, String url) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": in address bar go to URL " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterToUrl(Step step, URL url) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": go to URL " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterAlert(Step step, Alert alert) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": switch to alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDismiss(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": dismissed alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterAccept(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": accepted alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByAlert(Step step, String keysToSend) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": send text '" + keysToSend + "' to alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterClick(Step step, WebElement element) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": click on element " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterClear(Step step, WebElement element) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": clear input field " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": enter text '" + step.getParam2() + "' into input field " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSubmit(Step step, WebElement element) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": click on Submit button " + Step.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterExecuteAsyncScript(Step step, String script, Map<String, ?> params, Object result) {
		logEntries.add(step);
		String msg = "Step " + stepCounter + ": async executed script " + script;
		stepCounter++;
		buffer.append(msg).append(System.lineSeparator());
		if (!params.isEmpty()) {
			buffer.append(" with parameters ");
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			buffer.append(param2.substring(0, param2.length()-1));
		}
	}

	@Override
	public void afterExecuteScript(Step step, String script, Map<String, ?> params, Object result) {
		logEntries.add(step);
		String msg = "Step " + stepCounter + ": executed script " + script;
		stepCounter++;
		buffer.append(msg).append(System.lineSeparator());
		if (!params.isEmpty()) {
			buffer.append(" with parameters ");
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			buffer.append(param2.substring(0, param2.length()-1));
		}
	}

	@Override
	public void afterAddCookie(Step step, Cookie cookie) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": added cookie " + cookie.toString();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteCookieNamed(Step step, String name) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": deleted cookie " + name;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteCookie(Step step, Cookie cookie) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": deleted cookie " + cookie.getName();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteAllCookies(Step step) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": deleted all cookies";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterWindow(Step step, String windowName) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": switched to window " + windowName;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterUploadFile(Step step, WebElement element, File localFile, String response) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": uploaded file " + localFile.getName();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByKeyboard(Step step, CharSequence... keysToSend) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": entered text '" + keysToSend + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterPressKey(Step step, CharSequence... keyToPress) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": pressed key '" + keyToPress + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterReleaseKey(Step step, CharSequence... keyToRelease) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": released key '" + keyToRelease + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterClickByMouse(Step step, Coordinates where) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": left mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDoubleClick(Step step, Coordinates where) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": double mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseDown(Step step, Coordinates where) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": mouse down at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseMove(Step step, Coordinates where) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": mouse moved to page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseMove(Step step, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": mouse moved to page coordinates '" + where.onPage().toString()
				+ "' with x offset " + xOffset + " and y offset " + yOffset;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterContextClick(Step step, Coordinates where) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": context mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void onException(Step step, Cmd cmd, Throwable issue) {
		logEntries.add(step);
		String result = "Step " + stepCounter + ": command " + step.getCmd().getShortCmdString() + " failed with error " + issue.getMessage();
		if (result.equals(lastResultString)) {
			// don't repeat repeated failures
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public List<Step> getListOfEventsRecorded() {
		return Collections.unmodifiableList(logEntries);
	}
	
	@Override
	public String getEventsFormatted() {
		return buffer.toString();
	}
}
