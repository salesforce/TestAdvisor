//Licensed to the Software Freedom Conservancy (SFC) under one
//or more contributor license agreements.  See the NOTICE file
//distributed with this work for additional information
//regarding copyright ownership.  The SFC licenses this file
//to you under the Apache License, Version 2.0 (the
//"License"); you may not use this file except in compliance
//with the License.  You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing,
//software distributed under the License is distributed on an
//"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
//KIND, either express or implied.  See the License for the
//specific language governing permissions and limitations
//under the License.
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salesforce.cqe.driver.listener.Event.Cmd;

/**
 * Use this class as base class, if you want to implement a
 * {@link IEventListener} and are only interested in some events. All
 * methods provided by this class have an empty method body.
 * 
 * This is an extended version of org.openqa.selenium.support.events.AbstractWebDriverEventListener. See
 * https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/support/events/AbstractWebDriverEventListener.html
 * for more information.
 * 
 * @since 1.0
 */
public abstract class AbstractEventListener implements IEventListener {
	@JsonProperty("logEntries")
	protected List<Event> logEntries = new ArrayList<>();

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeClose(Event event) {
	}

	@Override
	public void afterClose(Event event) {
	}

	@Override
	public void beforeFindElementByWebDriver(Event event, By by) {
	}

	@Override
	public void afterFindElementByWebDriver(Event event, WebElement returnedElement, By by) {
	}

	@Override
	public void beforeFindElementsByWebDriver(Event event, By by) {
	}

	@Override
	public void afterFindElementsByWebDriver(Event event, List<WebElement> returnedElements, By by) {
	}

	@Override
	public void beforeGet(Event event, String url) {
	}

	@Override
	public void afterGet(Event event, String url) {
	}

	@Override
	public void beforeGetCurrentUrl(Event event) {
	}

	@Override
	public void afterGetCurrentUrl(Event event, String url) {
	}

	@Override
	public void beforeGetPageSource(Event event) {
	}

	@Override
	public void afterGetPageSource(Event event, String source) {
	}

	@Override
	public void beforeGetTitle(Event event) {
	}

	@Override
	public void afterGetTitle(Event event, String title) {
	}

	@Override
	public void beforeGetWindowHandle(Event event) {
	}

	@Override
	public void afterGetWindowHandle(Event event, String handle) {
	}

	@Override
	public void beforeGetWindowHandles(Event event) {
	}

	@Override
	public void afterGetWindowHandles(Event event, Set<String> handles) {
	}

	@Override
	public void beforeQuit(Event event) {
	}

	@Override
	public void afterQuit(Event event) {
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeExecuteAsyncScript(Event event, String script, Map<String, ?> params) {
	}

	@Override
	public void afterExecuteAsyncScript(Event event, String script, Map<String, ?> params, Object result) {
	}

	@Override
	public void beforeExecuteScript(Event event, String script, Map<String, ?> params) {
	}

	@Override
	public void afterExecuteScript(Event event, String script, Map<String, ?> params, Object result) {
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	@Override
	public <X> void beforeGetScreenshotAs(Event event, OutputType<X> target) {
	}

	@Override
	public <X> void afterGetScreenshotAs(Event event, OutputType<X> target, X screenshot) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeBack(Event event) {
	}

	@Override
	public void afterBack(Event event) {
	}

	@Override
	public void beforeForward(Event event) {
	}

	@Override
	public void afterForward(Event event) {
	}

	@Override
	public void beforeRefresh(Event event) {
	}

	@Override
	public void afterRefresh(Event event) {
	}

	@Override
	public void beforeTo(Event event, String url) {
	}

	@Override
	public void afterTo(Event event, String url) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeActiveElement(Event event) {
	}

	@Override
	public void afterActiveElement(Event event, WebElement activeElement) {
	}

	@Override
	public void beforeAlert(Event event) {
	}

	@Override
	public void afterAlert(Event event, Alert alert) {
	}

	@Override
	public void beforeDefaultContent(Event event) {
	}

	@Override
	public void afterDefaultContent(Event event) {
	}

	@Override
	public void beforeFrameByIndex(Event event, int frameIndex) {
	}

	@Override
	public void afterFrameByIndex(Event event, int frameIndex) {
	}

	@Override
	public void beforeFrameByName(Event event, String frameName) {
	}

	@Override
	public void afterFrameByName(Event event, String frameName) {
	}

	@Override
	public void beforeFrameByElement(Event event, WebElement frameElement) {
	}

	@Override
	public void afterFrameByElement(Event event, WebElement frameElement) {
	}

	@Override
	public void beforeParentFrame(Event event) {
	}

	@Override
	public void afterParentFrame(Event event) {
	}

	@Override
	public void beforeWindow(Event event, String windowName) {
	}

	@Override
	public void afterWindow(Event event, String windowName) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeImplicitlyWait(Event event, long time, TimeUnit unit) {
	}

	@Override
	public void afterImplicitlyWait(Event event, long time, TimeUnit unit) {
	}

	@Override
	public void beforePageLoadTimeout(Event event, long time, TimeUnit unit) {
	}

	@Override
	public void afterPageLoadTimeout(Event event, long time, TimeUnit unit) {
	}

	@Override
	public void beforeSetScriptTimeout(Event event, long time, TimeUnit unit) {
	}

	@Override
	public void afterSetScriptTimeout(Event event, long time, TimeUnit unit) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(Event event) {
	}

	@Override
	public void afterFullscreen(Event event) {
	}

	@Override
	public void beforeGetPosition(Event event) {
	}

	@Override
	public void afterGetPosition(Event event, Point targetPosition) {
	}

	@Override
	public void beforeGetSizeByWindow(Event event) {
	}

	@Override
	public void afterGetSizeByWindow(Event event, Dimension targetSize) {
	}

	@Override
	public void beforeMaximize(Event event) {
	}

	@Override
	public void afterMaximize(Event event) {
	}

	@Override
	public void beforeSetPosition(Event event, Point targetPosition) {
	}

	@Override
	public void afterSetPosition(Event event, Point targetPosition) {
	}

	@Override
	public void beforeSetSizeByWindow(Event event, Dimension targetSize) {
	}

	@Override
	public void afterSetSizeByWindow(Event event, Dimension targetSize) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(Event event, WebElement element) {
	}

	@Override
	public void afterClick(Event event, WebElement element) {
	}

	@Override
	public void beforeClear(Event event, WebElement element) {
	}

	@Override
	public void afterClear(Event event, WebElement element) {
	}

	@Override
	public void beforeFindElementByElement(Event event, By by, WebElement element) {
	}

	@Override
	public void afterFindElementByElement(Event event, WebElement returnedElement, By by, WebElement element) {
	}

	@Override
	public void beforeFindElementsByElement(Event event, By by, WebElement element) {
	}

	@Override
	public void afterFindElementsByElement(Event event, List<WebElement> returnedElements, By by, WebElement element) {
	}

	@Override
	public void beforeGetAttribute(Event event, String name, WebElement element) {
	}

	@Override
	public void afterGetAttribute(Event event, String value, String name, WebElement element) {
	}

	@Override
	public void beforeGetCssValue(Event event, String propertyName, WebElement element) {
	}

	@Override
	public void afterGetCssValue(Event event, String propertyName, String value, WebElement element) {
	}

	@Override
	public void beforeGetTagName(Event event, WebElement element) {
	}

	@Override
	public void afterGetTagName(Event event, String tagName, WebElement element) {
	}

	@Override
	public void beforeGetText(Event event, WebElement element) {
	}

	@Override
	public void afterGetText(Event event, String text, WebElement element) {
	}

	@Override
	public void beforeIsDisplayed(Event event, WebElement element) {
	}

	@Override
	public void afterIsDisplayed(Event event, boolean isDisplayed, WebElement element) {
	}

	@Override
	public void beforeIsEnabled(Event event, WebElement element) {
	}

	@Override
	public void afterIsEnabled(Event event, boolean isEnabled, WebElement element) {
	}

	@Override
	public void beforeIsSelected(Event event, WebElement element) {
	}

	@Override
	public void afterIsSelected(Event event, boolean isSelected, WebElement element) {
	}

	@Override
	public void beforeGetLocation(Event event, WebElement element) {
	}

	@Override
	public void afterGetLocation(Event event, Point point, WebElement element) {
	}

	@Override
	public void beforeGetSizeByElement(Event event, WebElement element) {
	}

	@Override
	public void afterGetSizeByElement(Event event, Dimension dimension, WebElement element) {
	}

	@Override
	public void beforeGetRect(Event event, WebElement element) {
	}

	@Override
	public void afterGetRect(Event event, Rectangle rectangle, WebElement element) {
	}

	@Override
	public void beforeSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void afterSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void beforeSubmit(Event event, WebElement element) {
	}

	@Override
	public void afterSubmit(Event event, WebElement element) {
	}

	@Override
	public void beforeSendKeysByKeyboard(Event event, CharSequence... keysToSend) {
	}

	@Override
	public void afterSendKeysByKeyboard(Event event, CharSequence... keysToSend) {
	}

	@Override
	public void beforePressKey(Event event, CharSequence... keyToPress) {
	}

	@Override
	public void afterPressKey(Event event, CharSequence... keyToPress) {
	}

	@Override
	public void beforeReleaseKey(Event event, CharSequence... keyToPress) {
	}

	@Override
	public void afterReleaseKey(Event event, CharSequence... keyToPress) {
	}

	@Override
	public void beforeClickByMouse(Event event, Coordinates where) {
	}

	@Override
	public void afterClickByMouse(Event event, Coordinates where) {
	}

	@Override
	public void beforeDoubleClick(Event event, Coordinates where) {
	}

	@Override
	public void afterDoubleClick(Event event, Coordinates where) {
	}

	@Override
	public void beforeMouseDown(Event event, Coordinates where) {
	}

	@Override
	public void afterMouseDown(Event event, Coordinates where) {
	}

	@Override
	public void beforeMouseUp(Event event, Coordinates where) {
	}

	@Override
	public void afterMouseUp(Event event, Coordinates where) {
	}

	@Override
	public void beforeMouseMove(Event event, Coordinates where) {
	}

	@Override
	public void afterMouseMove(Event event, Coordinates where) {
	}

	@Override
	public void beforeMouseMove(Event event, Coordinates where, long xOffset, long yOffset) {
	}

	@Override
	public void afterMouseMove(Event event, Coordinates where, long xOffset, long yOffset) {
	}

	@Override
	public void beforeContextClick(Event event, Coordinates where) {
	}

	@Override
	public void afterContextClick(Event event, Coordinates where) {
	}

	@Override
	public void onException(Event event, Cmd cmd, Throwable issue) {
	}

	/**
	 * The test name can contain characters which are not supported in a file name. This
	 * convenience method replaces such characters with underscores.
	 * @param testName test name
	 * @return file name using only characters supported by the OS
	 */
	public static String convertTestname2FileName(final String testName) {
		return testName.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
	}

	@Override
	public void beforeAddCookie(Event event, Cookie cookie) {
	}

	@Override
	public void afterAddCookie(Event event, Cookie cookie) {
	}

	@Override
	public void beforeDeleteCookieNamed(Event event, String name) {
	}

	@Override
	public void afterDeleteCookieNamed(Event event, String name) {
	}

	@Override
	public void beforeDeleteCookie(Event event, Cookie cookie) {
	}

	@Override
	public void afterDeleteCookie(Event event, Cookie cookie) {
	}

	@Override
	public void beforeDeleteAllCookies(Event event) {
	}

	@Override
	public void afterDeleteAllCookies(Event event) {
	}

	@Override
	public void beforeGetCookies(Event event) {
	}

	@Override
	public void afterGetCookies(Event event, Set<Cookie> cookies) {
	}

	@Override
	public void beforeGetCookieNamed(Event event, String name) {
	}

	@Override
	public void afterGetCookieNamed(Event event, String name, Cookie cookie) {
	}

	@Override
	public void beforeGetAvailableEngines(Event event) {
	}

	@Override
	public void afterGetAvailableEngines(Event event, List<String> engines) {
	}

	@Override
	public void beforeGetActiveEngine(Event event) {
	}

	@Override
	public void afterGetActiveEngine(Event event, String engine) {
	}

	@Override
	public void beforeIsActivated(Event event) {
	}

	@Override
	public void afterIsActivated(Event event, boolean isActive) {
	}

	@Override
	public void beforeDeactivate(Event event) {
	}

	@Override
	public void afterDeactivate(Event event) {
	}

	@Override
	public void beforeActivateEngine(Event event, String engine) {
	}

	@Override
	public void afterActivateEngine(Event event, String engine) {
	}

	@Override
	public void beforeToUrl(Event event, URL url) {
	}

	@Override
	public void afterToUrl(Event event, URL url) {
	}

	@Override
	public void beforeDismiss(Event event) {
	}

	@Override
	public void afterDismiss(Event event) {
	}

	@Override
	public void beforeAccept(Event event) {
	}

	@Override
	public void afterAccept(Event event) {
	}

	@Override
	public void beforeGetTextByAlert(Event event) {
	}

	@Override
	public void afterGetTextByAlert(Event event, String text) {
	}

	@Override
	public void beforeSendKeysByAlert(Event event, String keysToSend) {
	}

	@Override
	public void afterSendKeysByAlert(Event event, String keysToSend) {
	}

	@Override
	public void beforeGetCoordinates(Event event, WebElement element) {
	}

	@Override
	public void afterGetCoordinates(Event event, Coordinates coordinates, WebElement element) {
	}

	@Override
	public <X> void beforeGetScreenshotAsByElement(Event event, OutputType<X> target, WebElement element) {
	}

	@Override
	public <X> void afterGetScreenshotAsByElement(Event event, OutputType<X> target, X screenshot, WebElement element) {
	}

	@Override
	public void beforeUploadFile(Event event, WebElement element, File localFile) {
	}

	@Override
	public void afterUploadFile(Event event, WebElement element, File localFile, String response) {
	}

	@JsonProperty("logEntries")
	@Override
	public List<Event> getListOfEventsRecorded() {
		return Collections.unmodifiableList(logEntries);
	}
	
	@Override
	public String getEventsFormatted() {
		return null;
	}
}
