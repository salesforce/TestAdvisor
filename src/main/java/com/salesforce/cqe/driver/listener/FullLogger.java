/* 
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license. 
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.cqe.driver.listener;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;

import com.salesforce.cqe.admin.TestEvent;
import com.salesforce.cqe.driver.listener.WebDriverEvent.Cmd;

/**
 * Collects information on a given WebDriver command such as click() or getText() and saves this
 * collection to a JSON file.
 * 
 * The {@link org.openqa.selenium.remote.RemoteWebDriver}, {@link org.openqa.selenium.remote.RemoteWebElement},
 * {@link org.openqa.selenium.remote.RemoteKeyboard}, and {@link org.openqa.selenium.remote.RemoteMouse} have been patched
 * to meet DrillBit's needs and call {@link com.salesforce.cqe.driver.EventDispatcher} which creates {@link com.salesforce.cqe.driver.listener.WebDriverEvent}
 * objects before and after each WebDriver command. This class is a complete log all these Event objects.
 * 
 * @author gneumann
 * @since 1.0
 */
public class FullLogger extends AbstractEventListener {
	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeClose(WebDriverEvent event) {
		logEntries.add(event);
		
	}

	@Override
	public void afterClose(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementByWebDriver(WebDriverEvent event, By by) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementByWebDriver(WebDriverEvent event, WebElement returnedElement, By by) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementsByWebDriver(WebDriverEvent event, By by) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementsByWebDriver(WebDriverEvent event, List<WebElement> returnedElements, By by) {
		logEntries.add(event);
	}

	@Override
	public void beforeGet(WebDriverEvent event, String url) {
		logEntries.add(event);
	}

	@Override
	public void afterGet(WebDriverEvent event, String url) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCurrentUrl(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCurrentUrl(WebDriverEvent event, String url) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTitle(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTitle(WebDriverEvent event, String title) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetWindowHandle(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetWindowHandle(WebDriverEvent event, String handle) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetWindowHandles(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetWindowHandles(WebDriverEvent event, Set<String> handles) {
		logEntries.add(event);
	}

	@Override
	public void beforeQuit(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterQuit(WebDriverEvent event) {
		logEntries.add(event);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeExecuteAsyncScript(WebDriverEvent event, String script, Map<String, ?> params) {
		logEntries.add(event);
	}

	@Override
	public void afterExecuteAsyncScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
	}

	@Override
	public void beforeExecuteScript(WebDriverEvent event, String script, Map<String, ?> params) {
		logEntries.add(event);
	}

	@Override
	public void afterExecuteScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	@Override
	public <X> void beforeGetScreenshotAs(WebDriverEvent event, OutputType<X> target) {
		logEntries.add(event);
	}

	@Override
	public <X> void afterGetScreenshotAs(WebDriverEvent event, OutputType<X> target, X screenshot) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeBack(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterBack(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeForward(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterForward(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeRefresh(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterRefresh(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeTo(WebDriverEvent event, String url) {
		logEntries.add(event);
	}

	@Override
	public void afterTo(WebDriverEvent event, String url) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeActiveElement(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterActiveElement(WebDriverEvent event, WebElement activeElement) {
		logEntries.add(event);
	}

	@Override
	public void beforeAlert(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterAlert(WebDriverEvent event, Alert alert) {
		logEntries.add(event);
	}

	@Override
	public void beforeDefaultContent(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterDefaultContent(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByIndex(WebDriverEvent event, int frameIndex) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByIndex(WebDriverEvent event, int frameIndex) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByName(WebDriverEvent event, String frameName) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByName(WebDriverEvent event, String frameName) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByElement(WebDriverEvent event, WebElement frameElement) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByElement(WebDriverEvent event, WebElement frameElement) {
		logEntries.add(event);
	}

	@Override
	public void beforeParentFrame(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterParentFrame(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeWindow(WebDriverEvent event, String windowName) {
		logEntries.add(event);
	}

	@Override
	public void afterWindow(WebDriverEvent event, String windowName) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeImplicitlyWait(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterImplicitlyWait(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void beforePageLoadTimeout(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterPageLoadTimeout(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetScriptTimeout(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterSetScriptTimeout(WebDriverEvent event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterFullscreen(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetPosition(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetPosition(WebDriverEvent event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetSizeByWindow(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
		logEntries.add(event);
	}

	@Override
	public void beforeMaximize(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterMaximize(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetPosition(WebDriverEvent event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void afterSetPosition(WebDriverEvent event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
		logEntries.add(event);
	}

	@Override
	public void afterSetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterClick(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeClear(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterClear(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementByElement(WebDriverEvent event, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementByElement(WebDriverEvent event, WebElement returnedElement, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementsByElement(WebDriverEvent event, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementsByElement(WebDriverEvent event, List<WebElement> returnedElements, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetAttribute(WebDriverEvent event, String name, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetAttribute(WebDriverEvent event, String value, String name, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCssValue(WebDriverEvent event, String propertyName, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCssValue(WebDriverEvent event, String propertyName, String value, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTagName(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTagName(WebDriverEvent event, String tagName, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetText(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetText(WebDriverEvent event, String text, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsDisplayed(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsDisplayed(WebDriverEvent event, boolean isDisplayed, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsEnabled(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsEnabled(WebDriverEvent event, boolean isEnabled, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsSelected(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsSelected(WebDriverEvent event, boolean isSelected, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetLocation(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetLocation(WebDriverEvent event, Point point, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetSizeByElement(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetSizeByElement(WebDriverEvent event, Dimension dimension, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetRect(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetRect(WebDriverEvent event, Rectangle rectangle, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforeSubmit(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterSubmit(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByKeyboard(WebDriverEvent event, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByKeyboard(WebDriverEvent event, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforePressKey(WebDriverEvent event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void afterPressKey(WebDriverEvent event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void beforeReleaseKey(WebDriverEvent event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void afterReleaseKey(WebDriverEvent event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void beforeClickByMouse(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterClickByMouse(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeDoubleClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterDoubleClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseDown(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseDown(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseUp(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseUp(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseMove(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseMove(WebDriverEvent event, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(event);
	}

	@Override
	public void beforeContextClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterContextClick(WebDriverEvent event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetPageSource(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetPageSource(WebDriverEvent event, String source) {
		logEntries.add(event);
	}

	@Override
	public void beforeAddCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void afterAddCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteCookieNamed(WebDriverEvent event, String name) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteCookieNamed(WebDriverEvent event, String name) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteCookie(WebDriverEvent event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteAllCookies(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteAllCookies(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCookies(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCookies(WebDriverEvent event, Set<Cookie> cookies) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCookieNamed(WebDriverEvent event, String name) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCookieNamed(WebDriverEvent event, String name, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetAvailableEngines(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetAvailableEngines(WebDriverEvent event, List<String> engines) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetActiveEngine(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetActiveEngine(WebDriverEvent event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsActivated(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterIsActivated(WebDriverEvent event, boolean isActive) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeactivate(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterDeactivate(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeActivateEngine(WebDriverEvent event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void afterActivateEngine(WebDriverEvent event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void beforeToUrl(WebDriverEvent event, URL url) {
		logEntries.add(event);
	}

	@Override
	public void afterToUrl(WebDriverEvent event, URL url) {
		logEntries.add(event);
	}

	@Override
	public void beforeDismiss(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterDismiss(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeAccept(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterAccept(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTextByAlert(WebDriverEvent event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTextByAlert(WebDriverEvent event, String text) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByAlert(WebDriverEvent event, String keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByAlert(WebDriverEvent event, String keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCoordinates(WebDriverEvent event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCoordinates(WebDriverEvent event, Coordinates coordinates, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public <X> void beforeGetScreenshotAsByElement(WebDriverEvent event, OutputType<X> target, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public <X> void afterGetScreenshotAsByElement(WebDriverEvent event, OutputType<X> target, X screenshot, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeUploadFile(WebDriverEvent event, WebElement element, File localFile) {
		logEntries.add(event);
	}

	@Override
	public void afterUploadFile(WebDriverEvent event, WebElement element, File localFile, String response) {
		logEntries.add(event);
	}

	@Override
	public void onException(WebDriverEvent event, Cmd cmd, Throwable issue) {
		logEntries.add(event);
		drillbitAdministrator.getTestCaseExecution().appendEvent(new TestEvent(event.toString(), Level.WARNING));
	}
	
	// This listener provides the events in the way defined in AbstractEventListener.
}
