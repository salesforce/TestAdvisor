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

import com.salesforce.cqe.driver.listener.WebDriverEvent.Cmd;

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
	public void afterClose(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": close tab";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterGet(WebDriverEvent event, String url) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": open page " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterQuit(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": close browser";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterBack(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": press Back button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterForward(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": press Forward button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterRefresh(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": press Refresh button";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterTo(WebDriverEvent event, String url) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": in address bar go to URL " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterToUrl(WebDriverEvent event, URL url) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": go to URL " + url;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterAlert(WebDriverEvent event, Alert alert) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": switch to alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDismiss(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": dismissed alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterAccept(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": accepted alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByAlert(WebDriverEvent event, String keysToSend) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": send text '" + keysToSend + "' to alert dialog.";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void afterClick(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": click on element " + WebDriverEvent.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterClear(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": clear input field " + WebDriverEvent.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": enter text '" + event.getParam2() + "' into input field " + WebDriverEvent.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSubmit(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": click on Submit button " + WebDriverEvent.getLocatorFromWebElement(element);
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterExecuteAsyncScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
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
	public void afterExecuteScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
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
	public void afterAddCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": added cookie " + cookie.toString();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteCookieNamed(WebDriverEvent event, String name) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": deleted cookie " + name;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": deleted cookie " + cookie.getName();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDeleteAllCookies(WebDriverEvent event) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": deleted all cookies";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterWindow(WebDriverEvent event, String windowName) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": switched to window " + windowName;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterUploadFile(WebDriverEvent event, WebElement element, File localFile, String response) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": uploaded file " + localFile.getName();
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterSendKeysByKeyboard(WebDriverEvent event, CharSequence... keysToSend) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": entered text '" + keysToSend + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterPressKey(WebDriverEvent event, CharSequence... keyToPress) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": pressed key '" + keyToPress + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterReleaseKey(WebDriverEvent event, CharSequence... keyToRelease) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": released key '" + keyToRelease + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterClickByMouse(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": left mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterDoubleClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": double mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseDown(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": mouse down at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": mouse moved to page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": mouse moved to page coordinates '" + where.onPage().toString()
				+ "' with x offset " + xOffset + " and y offset " + yOffset;
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void afterContextClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": context mouse click at page coordinates '" + where.onPage().toString() + "'";
		stepCounter++;
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public void onException(WebDriverEvent event, Cmd cmd, Throwable issue) {
		logEntries.add(event);
		String result = "Step " + stepCounter + ": command " + event.getCmd().getShortCmdString() + " failed with error " + issue.getMessage();
		if (result.equals(lastResultString)) {
			// don't repeat repeated failures
			return;
		} else {
			lastResultString = result;
		}
		buffer.append(result).append(System.lineSeparator());
	}

	@Override
	public List<WebDriverEvent> getListOfEventsRecorded() {
		return Collections.unmodifiableList(logEntries);
	}
	
	@Override
	public String getEventsFormatted() {
		return buffer.toString();
	}
}
