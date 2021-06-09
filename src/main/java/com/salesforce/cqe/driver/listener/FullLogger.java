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

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;

import com.salesforce.cqe.driver.listener.Event.Cmd;

/**
 * Collects information on a given WebDriver command such as click() or getText() and saves this
 * collection to a JSON file.
 * 
 * The {@link org.openqa.selenium.remote.RemoteWebDriver}, {@link org.openqa.selenium.remote.RemoteWebElement},
 * {@link org.openqa.selenium.remote.RemoteKeyboard}, and {@link org.openqa.selenium.remote.RemoteMouse} have been patched
 * to meet DrillBit's needs and call {@link com.salesforce.cqe.driver.EventDispatcher} which creates {@link com.salesforce.cqe.driver.listener.Event}
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
	public void beforeClose(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterClose(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementByWebDriver(Event event, By by) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementByWebDriver(Event event, WebElement returnedElement, By by) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementsByWebDriver(Event event, By by) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementsByWebDriver(Event event, List<WebElement> returnedElements, By by) {
		logEntries.add(event);
	}

	@Override
	public void beforeGet(Event event, String url) {
		logEntries.add(event);
	}

	@Override
	public void afterGet(Event event, String url) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCurrentUrl(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCurrentUrl(Event event, String url) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTitle(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTitle(Event event, String title) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetWindowHandle(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetWindowHandle(Event event, String handle) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetWindowHandles(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetWindowHandles(Event event, Set<String> handles) {
		logEntries.add(event);
	}

	@Override
	public void beforeQuit(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterQuit(Event event) {
		logEntries.add(event);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeExecuteAsyncScript(Event event, String script, Map<String, ?> params) {
		logEntries.add(event);
	}

	@Override
	public void afterExecuteAsyncScript(Event event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
	}

	@Override
	public void beforeExecuteScript(Event event, String script, Map<String, ?> params) {
		logEntries.add(event);
	}

	@Override
	public void afterExecuteScript(Event event, String script, Map<String, ?> params, Object result) {
		logEntries.add(event);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	@Override
	public <X> void beforeGetScreenshotAs(Event event, OutputType<X> target) {
		logEntries.add(event);
	}

	@Override
	public <X> void afterGetScreenshotAs(Event event, OutputType<X> target, X screenshot) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeBack(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterBack(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeForward(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterForward(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeRefresh(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterRefresh(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeTo(Event event, String url) {
		logEntries.add(event);
	}

	@Override
	public void afterTo(Event event, String url) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeActiveElement(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterActiveElement(Event event, WebElement activeElement) {
		logEntries.add(event);
	}

	@Override
	public void beforeAlert(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterAlert(Event event, Alert alert) {
		logEntries.add(event);
	}

	@Override
	public void beforeDefaultContent(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterDefaultContent(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByIndex(Event event, int frameIndex) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByIndex(Event event, int frameIndex) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByName(Event event, String frameName) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByName(Event event, String frameName) {
		logEntries.add(event);
	}

	@Override
	public void beforeFrameByElement(Event event, WebElement frameElement) {
		logEntries.add(event);
	}

	@Override
	public void afterFrameByElement(Event event, WebElement frameElement) {
		logEntries.add(event);
	}

	@Override
	public void beforeParentFrame(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterParentFrame(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeWindow(Event event, String windowName) {
		logEntries.add(event);
	}

	@Override
	public void afterWindow(Event event, String windowName) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeImplicitlyWait(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterImplicitlyWait(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void beforePageLoadTimeout(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterPageLoadTimeout(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetScriptTimeout(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	@Override
	public void afterSetScriptTimeout(Event event, long time, TimeUnit unit) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterFullscreen(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetPosition(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetPosition(Event event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetSizeByWindow(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetSizeByWindow(Event event, Dimension targetSize) {
		logEntries.add(event);
	}

	@Override
	public void beforeMaximize(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterMaximize(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetPosition(Event event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void afterSetPosition(Event event, Point targetPosition) {
		logEntries.add(event);
	}

	@Override
	public void beforeSetSizeByWindow(Event event, Dimension targetSize) {
		logEntries.add(event);
	}

	@Override
	public void afterSetSizeByWindow(Event event, Dimension targetSize) {
		logEntries.add(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterClick(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeClear(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterClear(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementByElement(Event event, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementByElement(Event event, WebElement returnedElement, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeFindElementsByElement(Event event, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterFindElementsByElement(Event event, List<WebElement> returnedElements, By by, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetAttribute(Event event, String name, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetAttribute(Event event, String value, String name, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCssValue(Event event, String propertyName, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCssValue(Event event, String propertyName, String value, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTagName(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTagName(Event event, String tagName, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetText(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetText(Event event, String text, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsDisplayed(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsDisplayed(Event event, boolean isDisplayed, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsEnabled(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsEnabled(Event event, boolean isEnabled, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsSelected(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterIsSelected(Event event, boolean isSelected, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetLocation(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetLocation(Event event, Point point, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetSizeByElement(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetSizeByElement(Event event, Dimension dimension, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetRect(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetRect(Event event, Rectangle rectangle, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforeSubmit(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterSubmit(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByKeyboard(Event event, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByKeyboard(Event event, CharSequence... keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforePressKey(Event event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void afterPressKey(Event event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void beforeReleaseKey(Event event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void afterReleaseKey(Event event, CharSequence... keyToPress) {
		logEntries.add(event);
	}

	@Override
	public void beforeClickByMouse(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterClickByMouse(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeDoubleClick(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterDoubleClick(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseDown(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseDown(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseUp(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseUp(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseMove(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseMove(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeMouseMove(Event event, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(event);
	}

	@Override
	public void afterMouseMove(Event event, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(event);
	}

	@Override
	public void beforeContextClick(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void afterContextClick(Event event, Coordinates where) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetPageSource(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetPageSource(Event event, String source) {
		logEntries.add(event);
	}

	@Override
	public void beforeAddCookie(Event event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void afterAddCookie(Event event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteCookieNamed(Event event, String name) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteCookieNamed(Event event, String name) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteCookie(Event event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteCookie(Event event, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeleteAllCookies(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterDeleteAllCookies(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCookies(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCookies(Event event, Set<Cookie> cookies) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCookieNamed(Event event, String name) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCookieNamed(Event event, String name, Cookie cookie) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetAvailableEngines(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetAvailableEngines(Event event, List<String> engines) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetActiveEngine(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetActiveEngine(Event event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void beforeIsActivated(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterIsActivated(Event event, boolean isActive) {
		logEntries.add(event);
	}

	@Override
	public void beforeDeactivate(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterDeactivate(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeActivateEngine(Event event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void afterActivateEngine(Event event, String engine) {
		logEntries.add(event);
	}

	@Override
	public void beforeToUrl(Event event, URL url) {
		logEntries.add(event);
	}

	@Override
	public void afterToUrl(Event event, URL url) {
		logEntries.add(event);
	}

	@Override
	public void beforeDismiss(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterDismiss(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeAccept(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterAccept(Event event) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetTextByAlert(Event event) {
		logEntries.add(event);
	}

	@Override
	public void afterGetTextByAlert(Event event, String text) {
		logEntries.add(event);
	}

	@Override
	public void beforeSendKeysByAlert(Event event, String keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void afterSendKeysByAlert(Event event, String keysToSend) {
		logEntries.add(event);
	}

	@Override
	public void beforeGetCoordinates(Event event, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void afterGetCoordinates(Event event, Coordinates coordinates, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public <X> void beforeGetScreenshotAsByElement(Event event, OutputType<X> target, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public <X> void afterGetScreenshotAsByElement(Event event, OutputType<X> target, X screenshot, WebElement element) {
		logEntries.add(event);
	}

	@Override
	public void beforeUploadFile(Event event, WebElement element, File localFile) {
		logEntries.add(event);
	}

	@Override
	public void afterUploadFile(Event event, WebElement element, File localFile, String response) {
		logEntries.add(event);
	}

	@Override
	public void onException(Event event, Cmd cmd, Throwable issue) {
		logEntries.add(event);
	}
	
	// This listener provides the events in the way defined in AbstractEventListener.
}
