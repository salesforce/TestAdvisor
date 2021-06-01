/* 
 * Copyright (c) 2018, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license. 
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.cqe.driver.listener;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

import com.salesforce.cqe.driver.listener.Step.Cmd;

/**
 * Collects information on a given WebDriver command such as click() or getText() and saves this
 * collection to a JSON file.
 * 
 * The {@link org.openqa.selenium.remote.RemoteWebDriver}, {@link org.openqa.selenium.remote.RemoteWebElement},
 * {@link org.openqa.selenium.remote.RemoteKeyboard}, and {@link org.openqa.selenium.remote.RemoteMouse} have been patched
 * to meet DrillBit's needs and call {@link com.salesforce.cqe.driver.EventDispatcher} which creates {@link com.salesforce.cqe.driver.listener.Step}
 * objects before and after each WebDriver command. This class is a complete log all these Step objects.
 * 
 * @author gneumann
 * @since 1.0
 */
public class FullLogger extends AbstractEventListener {
	private List<Step> logEntries = new ArrayList<>();

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeClose(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterClose(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeFindElementByWebDriver(Step step, By by) {
		logEntries.add(step);
	}

	@Override
	public void afterFindElementByWebDriver(Step step, WebElement returnedElement, By by) {
		logEntries.add(step);
	}

	@Override
	public void beforeFindElementsByWebDriver(Step step, By by) {
		logEntries.add(step);
	}

	@Override
	public void afterFindElementsByWebDriver(Step step, List<WebElement> returnedElements, By by) {
		logEntries.add(step);
	}

	@Override
	public void beforeGet(Step step, String url) {
		logEntries.add(step);
	}

	@Override
	public void afterGet(Step step, String url) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetCurrentUrl(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetCurrentUrl(Step step, String url) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetTitle(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetTitle(Step step, String title) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetWindowHandle(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetWindowHandle(Step step, String handle) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetWindowHandles(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetWindowHandles(Step step, Set<String> handles) {
		logEntries.add(step);
	}

	@Override
	public void beforeQuit(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterQuit(Step step) {
		logEntries.add(step);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeExecuteAsyncScript(Step step, String script, Map<String, ?> params) {
		logEntries.add(step);
	}

	@Override
	public void afterExecuteAsyncScript(Step step, String script, Map<String, ?> params, Object result) {
		logEntries.add(step);
	}

	@Override
	public void beforeExecuteScript(Step step, String script, Map<String, ?> params) {
		logEntries.add(step);
	}

	@Override
	public void afterExecuteScript(Step step, String script, Map<String, ?> params, Object result) {
		logEntries.add(step);
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	@Override
	public <X> void beforeGetScreenshotAs(Step step, OutputType<X> target) {
		logEntries.add(step);
	}

	@Override
	public <X> void afterGetScreenshotAs(Step step, OutputType<X> target, X screenshot) {
		logEntries.add(step);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeBack(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterBack(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeForward(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterForward(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeRefresh(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterRefresh(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeTo(Step step, String url) {
		logEntries.add(step);
	}

	@Override
	public void afterTo(Step step, String url) {
		logEntries.add(step);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeActiveElement(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterActiveElement(Step step, WebElement activeElement) {
		logEntries.add(step);
	}

	@Override
	public void beforeAlert(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterAlert(Step step, Alert alert) {
		logEntries.add(step);
	}

	@Override
	public void beforeDefaultContent(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterDefaultContent(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeFrameByIndex(Step step, int frameIndex) {
		logEntries.add(step);
	}

	@Override
	public void afterFrameByIndex(Step step, int frameIndex) {
		logEntries.add(step);
	}

	@Override
	public void beforeFrameByName(Step step, String frameName) {
		logEntries.add(step);
	}

	@Override
	public void afterFrameByName(Step step, String frameName) {
		logEntries.add(step);
	}

	@Override
	public void beforeFrameByElement(Step step, WebElement frameElement) {
		logEntries.add(step);
	}

	@Override
	public void afterFrameByElement(Step step, WebElement frameElement) {
		logEntries.add(step);
	}

	@Override
	public void beforeParentFrame(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterParentFrame(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeWindow(Step step, String windowName) {
		logEntries.add(step);
	}

	@Override
	public void afterWindow(Step step, String windowName) {
		logEntries.add(step);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeImplicitlyWait(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	@Override
	public void afterImplicitlyWait(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	@Override
	public void beforePageLoadTimeout(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	@Override
	public void afterPageLoadTimeout(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	@Override
	public void beforeSetScriptTimeout(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	@Override
	public void afterSetScriptTimeout(Step step, long time, TimeUnit unit) {
		logEntries.add(step);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterFullscreen(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetPosition(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetPosition(Step step, Point targetPosition) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetSizeByWindow(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetSizeByWindow(Step step, Dimension targetSize) {
		logEntries.add(step);
	}

	@Override
	public void beforeMaximize(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterMaximize(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeSetPosition(Step step, Point targetPosition) {
		logEntries.add(step);
	}

	@Override
	public void afterSetPosition(Step step, Point targetPosition) {
		logEntries.add(step);
	}

	@Override
	public void beforeSetSizeByWindow(Step step, Dimension targetSize) {
		logEntries.add(step);
	}

	@Override
	public void afterSetSizeByWindow(Step step, Dimension targetSize) {
		logEntries.add(step);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterClick(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeClear(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterClear(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeFindElementByElement(Step step, By by, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterFindElementByElement(Step step, WebElement returnedElement, By by, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeFindElementsByElement(Step step, By by, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterFindElementsByElement(Step step, List<WebElement> returnedElements, By by, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetAttribute(Step step, String name, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetAttribute(Step step, String value, String name, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetCssValue(Step step, String propertyName, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetCssValue(Step step, String propertyName, String value, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetTagName(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetTagName(Step step, String tagName, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetText(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetText(Step step, String text, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeIsDisplayed(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterIsDisplayed(Step step, boolean isDisplayed, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeIsEnabled(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterIsEnabled(Step step, boolean isEnabled, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeIsSelected(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterIsSelected(Step step, boolean isSelected, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetLocation(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetLocation(Step step, Point point, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetSizeByElement(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetSizeByElement(Step step, Dimension dimension, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetRect(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetRect(Step step, Rectangle rectangle, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void afterSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void beforeSubmit(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterSubmit(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeSendKeysByKeyboard(Step step, CharSequence... keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void afterSendKeysByKeyboard(Step step, CharSequence... keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void beforePressKey(Step step, CharSequence... keyToPress) {
		logEntries.add(step);
	}

	@Override
	public void afterPressKey(Step step, CharSequence... keyToPress) {
		logEntries.add(step);
	}

	@Override
	public void beforeReleaseKey(Step step, CharSequence... keyToPress) {
		logEntries.add(step);
	}

	@Override
	public void afterReleaseKey(Step step, CharSequence... keyToPress) {
		logEntries.add(step);
	}

	@Override
	public void beforeClickByMouse(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterClickByMouse(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeDoubleClick(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterDoubleClick(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeMouseDown(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterMouseDown(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeMouseUp(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterMouseUp(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeMouseMove(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterMouseMove(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeMouseMove(Step step, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(step);
	}

	@Override
	public void afterMouseMove(Step step, Coordinates where, long xOffset, long yOffset) {
		logEntries.add(step);
	}

	@Override
	public void beforeContextClick(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void afterContextClick(Step step, Coordinates where) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetPageSource(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetPageSource(Step step, String source) {
		logEntries.add(step);
	}

	@Override
	public void beforeAddCookie(Step step, Cookie cookie) {
		logEntries.add(step);
	}

	@Override
	public void afterAddCookie(Step step, Cookie cookie) {
		logEntries.add(step);
	}

	@Override
	public void beforeDeleteCookieNamed(Step step, String name) {
		logEntries.add(step);
	}

	@Override
	public void afterDeleteCookieNamed(Step step, String name) {
		logEntries.add(step);
	}

	@Override
	public void beforeDeleteCookie(Step step, Cookie cookie) {
		logEntries.add(step);
	}

	@Override
	public void afterDeleteCookie(Step step, Cookie cookie) {
		logEntries.add(step);
	}

	@Override
	public void beforeDeleteAllCookies(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterDeleteAllCookies(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetCookies(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetCookies(Step step, Set<Cookie> cookies) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetCookieNamed(Step step, String name) {
		logEntries.add(step);
	}

	@Override
	public void afterGetCookieNamed(Step step, String name, Cookie cookie) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetAvailableEngines(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetAvailableEngines(Step step, List<String> engines) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetActiveEngine(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetActiveEngine(Step step, String engine) {
		logEntries.add(step);
	}

	@Override
	public void beforeIsActivated(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterIsActivated(Step step, boolean isActive) {
		logEntries.add(step);
	}

	@Override
	public void beforeDeactivate(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterDeactivate(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeActivateEngine(Step step, String engine) {
		logEntries.add(step);
	}

	@Override
	public void afterActivateEngine(Step step, String engine) {
		logEntries.add(step);
	}

	@Override
	public void beforeToUrl(Step step, URL url) {
		logEntries.add(step);
	}

	@Override
	public void afterToUrl(Step step, URL url) {
		logEntries.add(step);
	}

	@Override
	public void beforeDismiss(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterDismiss(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeAccept(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterAccept(Step step) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetTextByAlert(Step step) {
		logEntries.add(step);
	}

	@Override
	public void afterGetTextByAlert(Step step, String text) {
		logEntries.add(step);
	}

	@Override
	public void beforeSendKeysByAlert(Step step, String keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void afterSendKeysByAlert(Step step, String keysToSend) {
		logEntries.add(step);
	}

	@Override
	public void beforeGetCoordinates(Step step, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void afterGetCoordinates(Step step, Coordinates coordinates, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public <X> void beforeGetScreenshotAsByElement(Step step, OutputType<X> target, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public <X> void afterGetScreenshotAsByElement(Step step, OutputType<X> target, X screenshot, WebElement element) {
		logEntries.add(step);
	}

	@Override
	public void beforeUploadFile(Step step, WebElement element, File localFile) {
		logEntries.add(step);
	}

	@Override
	public void afterUploadFile(Step step, WebElement element, File localFile, String response) {
		logEntries.add(step);
	}

	@Override
	public void onException(Step step, Cmd cmd, Throwable issue) {
		logEntries.add(step);
	}
	
	/**
	 * Gets the currently logged list of steps.
	 * @return immutable list of steps
	 */
	public List<Step> getImmutableListOfSteps() {
		return Collections.unmodifiableList(logEntries);
	}
}
