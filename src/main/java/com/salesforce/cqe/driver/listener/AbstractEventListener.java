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
import com.salesforce.cqe.admin.DrillBitAdministrator;
import com.salesforce.cqe.admin.TestCaseExecution;
import com.salesforce.cqe.driver.listener.WebDriverEvent.Cmd;

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
	protected List<WebDriverEvent> logEntries = new ArrayList<>();
	protected TestCaseExecution testCaseExecution = DrillBitAdministrator.getInstance().getTestCaseExecution();

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeClose(WebDriverEvent event) {
	}

	@Override
	public void afterClose(WebDriverEvent event) {
	}

	@Override
	public void beforeFindElementByWebDriver(WebDriverEvent event, By by) {
	}

	@Override
	public void afterFindElementByWebDriver(WebDriverEvent event, WebElement returnedElement, By by) {
	}

	@Override
	public void beforeFindElementsByWebDriver(WebDriverEvent event, By by) {
	}

	@Override
	public void afterFindElementsByWebDriver(WebDriverEvent event, List<WebElement> returnedElements, By by) {
	}

	@Override
	public void beforeGet(WebDriverEvent event, String url) {
	}

	@Override
	public void afterGet(WebDriverEvent event, String url) {
	}

	@Override
	public void beforeGetCurrentUrl(WebDriverEvent event) {
	}

	@Override
	public void afterGetCurrentUrl(WebDriverEvent event, String url) {
	}

	@Override
	public void beforeGetPageSource(WebDriverEvent event) {
	}

	@Override
	public void afterGetPageSource(WebDriverEvent event, String source) {
	}

	@Override
	public void beforeGetTitle(WebDriverEvent event) {
	}

	@Override
	public void afterGetTitle(WebDriverEvent event, String title) {
	}

	@Override
	public void beforeGetWindowHandle(WebDriverEvent event) {
	}

	@Override
	public void afterGetWindowHandle(WebDriverEvent event, String handle) {
	}

	@Override
	public void beforeGetWindowHandles(WebDriverEvent event) {
	}

	@Override
	public void afterGetWindowHandles(WebDriverEvent event, Set<String> handles) {
	}

	@Override
	public void beforeQuit(WebDriverEvent event) {
	}

	@Override
	public void afterQuit(WebDriverEvent event) {
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	@Override
	public void beforeExecuteAsyncScript(WebDriverEvent event, String script, Map<String, ?> params) {
	}

	@Override
	public void afterExecuteAsyncScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
	}

	@Override
	public void beforeExecuteScript(WebDriverEvent event, String script, Map<String, ?> params) {
	}

	@Override
	public void afterExecuteScript(WebDriverEvent event, String script, Map<String, ?> params, Object result) {
	}

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	@Override
	public <X> void beforeGetScreenshotAs(WebDriverEvent event, OutputType<X> target) {
	}

	@Override
	public <X> void afterGetScreenshotAs(WebDriverEvent event, OutputType<X> target, X screenshot) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeBack(WebDriverEvent event) {
	}

	@Override
	public void afterBack(WebDriverEvent event) {
	}

	@Override
	public void beforeForward(WebDriverEvent event) {
	}

	@Override
	public void afterForward(WebDriverEvent event) {
	}

	@Override
	public void beforeRefresh(WebDriverEvent event) {
	}

	@Override
	public void afterRefresh(WebDriverEvent event) {
	}

	@Override
	public void beforeTo(WebDriverEvent event, String url) {
	}

	@Override
	public void afterTo(WebDriverEvent event, String url) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeActiveElement(WebDriverEvent event) {
	}

	@Override
	public void afterActiveElement(WebDriverEvent event, WebElement activeElement) {
	}

	@Override
	public void beforeAlert(WebDriverEvent event) {
	}

	@Override
	public void afterAlert(WebDriverEvent event, Alert alert) {
	}

	@Override
	public void beforeDefaultContent(WebDriverEvent event) {
	}

	@Override
	public void afterDefaultContent(WebDriverEvent event) {
	}

	@Override
	public void beforeFrameByIndex(WebDriverEvent event, int frameIndex) {
	}

	@Override
	public void afterFrameByIndex(WebDriverEvent event, int frameIndex) {
	}

	@Override
	public void beforeFrameByName(WebDriverEvent event, String frameName) {
	}

	@Override
	public void afterFrameByName(WebDriverEvent event, String frameName) {
	}

	@Override
	public void beforeFrameByElement(WebDriverEvent event, WebElement frameElement) {
	}

	@Override
	public void afterFrameByElement(WebDriverEvent event, WebElement frameElement) {
	}

	@Override
	public void beforeParentFrame(WebDriverEvent event) {
	}

	@Override
	public void afterParentFrame(WebDriverEvent event) {
	}

	@Override
	public void beforeWindow(WebDriverEvent event, String windowName) {
	}

	@Override
	public void afterWindow(WebDriverEvent event, String windowName) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeImplicitlyWait(WebDriverEvent event, long time, TimeUnit unit) {
	}

	@Override
	public void afterImplicitlyWait(WebDriverEvent event, long time, TimeUnit unit) {
	}

	@Override
	public void beforePageLoadTimeout(WebDriverEvent event, long time, TimeUnit unit) {
	}

	@Override
	public void afterPageLoadTimeout(WebDriverEvent event, long time, TimeUnit unit) {
	}

	@Override
	public void beforeSetScriptTimeout(WebDriverEvent event, long time, TimeUnit unit) {
	}

	@Override
	public void afterSetScriptTimeout(WebDriverEvent event, long time, TimeUnit unit) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(WebDriverEvent event) {
	}

	@Override
	public void afterFullscreen(WebDriverEvent event) {
	}

	@Override
	public void beforeGetPosition(WebDriverEvent event) {
	}

	@Override
	public void afterGetPosition(WebDriverEvent event, Point targetPosition) {
	}

	@Override
	public void beforeGetSizeByWindow(WebDriverEvent event) {
	}

	@Override
	public void afterGetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
	}

	@Override
	public void beforeMaximize(WebDriverEvent event) {
	}

	@Override
	public void afterMaximize(WebDriverEvent event) {
	}

	@Override
	public void beforeSetPosition(WebDriverEvent event, Point targetPosition) {
	}

	@Override
	public void afterSetPosition(WebDriverEvent event, Point targetPosition) {
	}

	@Override
	public void beforeSetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
	}

	@Override
	public void afterSetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterClick(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void beforeClear(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterClear(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void beforeFindElementByElement(WebDriverEvent event, By by, WebElement element) {
	}

	@Override
	public void afterFindElementByElement(WebDriverEvent event, WebElement returnedElement, By by, WebElement element) {
	}

	@Override
	public void beforeFindElementsByElement(WebDriverEvent event, By by, WebElement element) {
	}

	@Override
	public void afterFindElementsByElement(WebDriverEvent event, List<WebElement> returnedElements, By by, WebElement element) {
	}

	@Override
	public void beforeGetAttribute(WebDriverEvent event, String name, WebElement element) {
	}

	@Override
	public void afterGetAttribute(WebDriverEvent event, String value, String name, WebElement element) {
	}

	@Override
	public void beforeGetCssValue(WebDriverEvent event, String propertyName, WebElement element) {
	}

	@Override
	public void afterGetCssValue(WebDriverEvent event, String propertyName, String value, WebElement element) {
	}

	@Override
	public void beforeGetTagName(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetTagName(WebDriverEvent event, String tagName, WebElement element) {
	}

	@Override
	public void beforeGetText(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetText(WebDriverEvent event, String text, WebElement element) {
	}

	@Override
	public void beforeIsDisplayed(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterIsDisplayed(WebDriverEvent event, boolean isDisplayed, WebElement element) {
	}

	@Override
	public void beforeIsEnabled(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterIsEnabled(WebDriverEvent event, boolean isEnabled, WebElement element) {
	}

	@Override
	public void beforeIsSelected(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterIsSelected(WebDriverEvent event, boolean isSelected, WebElement element) {
	}

	@Override
	public void beforeGetLocation(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetLocation(WebDriverEvent event, Point point, WebElement element) {
	}

	@Override
	public void beforeGetSizeByElement(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetSizeByElement(WebDriverEvent event, Dimension dimension, WebElement element) {
	}

	@Override
	public void beforeGetRect(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetRect(WebDriverEvent event, Rectangle rectangle, WebElement element) {
	}

	@Override
	public void beforeSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void afterSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
	}

	@Override
	public void beforeSubmit(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterSubmit(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void beforeSendKeysByKeyboard(WebDriverEvent event, CharSequence... keysToSend) {
	}

	@Override
	public void afterSendKeysByKeyboard(WebDriverEvent event, CharSequence... keysToSend) {
	}

	@Override
	public void beforePressKey(WebDriverEvent event, CharSequence... keyToPress) {
	}

	@Override
	public void afterPressKey(WebDriverEvent event, CharSequence... keyToPress) {
	}

	@Override
	public void beforeReleaseKey(WebDriverEvent event, CharSequence... keyToPress) {
	}

	@Override
	public void afterReleaseKey(WebDriverEvent event, CharSequence... keyToPress) {
	}

	@Override
	public void beforeClickByMouse(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterClickByMouse(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void beforeDoubleClick(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterDoubleClick(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void beforeMouseDown(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterMouseDown(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void beforeMouseUp(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterMouseUp(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void beforeMouseMove(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void beforeMouseMove(WebDriverEvent event, Coordinates where, long xOffset, long yOffset) {
	}

	@Override
	public void afterMouseMove(WebDriverEvent event, Coordinates where, long xOffset, long yOffset) {
	}

	@Override
	public void beforeContextClick(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void afterContextClick(WebDriverEvent event, Coordinates where) {
	}

	@Override
	public void onException(WebDriverEvent event, Cmd cmd, Throwable issue) {
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
	public void beforeAddCookie(WebDriverEvent event, Cookie cookie) {
	}

	@Override
	public void afterAddCookie(WebDriverEvent event, Cookie cookie) {
	}

	@Override
	public void beforeDeleteCookieNamed(WebDriverEvent event, String name) {
	}

	@Override
	public void afterDeleteCookieNamed(WebDriverEvent event, String name) {
	}

	@Override
	public void beforeDeleteCookie(WebDriverEvent event, Cookie cookie) {
	}

	@Override
	public void afterDeleteCookie(WebDriverEvent event, Cookie cookie) {
	}

	@Override
	public void beforeDeleteAllCookies(WebDriverEvent event) {
	}

	@Override
	public void afterDeleteAllCookies(WebDriverEvent event) {
	}

	@Override
	public void beforeGetCookies(WebDriverEvent event) {
	}

	@Override
	public void afterGetCookies(WebDriverEvent event, Set<Cookie> cookies) {
	}

	@Override
	public void beforeGetCookieNamed(WebDriverEvent event, String name) {
	}

	@Override
	public void afterGetCookieNamed(WebDriverEvent event, String name, Cookie cookie) {
	}

	@Override
	public void beforeGetAvailableEngines(WebDriverEvent event) {
	}

	@Override
	public void afterGetAvailableEngines(WebDriverEvent event, List<String> engines) {
	}

	@Override
	public void beforeGetActiveEngine(WebDriverEvent event) {
	}

	@Override
	public void afterGetActiveEngine(WebDriverEvent event, String engine) {
	}

	@Override
	public void beforeIsActivated(WebDriverEvent event) {
	}

	@Override
	public void afterIsActivated(WebDriverEvent event, boolean isActive) {
	}

	@Override
	public void beforeDeactivate(WebDriverEvent event) {
	}

	@Override
	public void afterDeactivate(WebDriverEvent event) {
	}

	@Override
	public void beforeActivateEngine(WebDriverEvent event, String engine) {
	}

	@Override
	public void afterActivateEngine(WebDriverEvent event, String engine) {
	}

	@Override
	public void beforeToUrl(WebDriverEvent event, URL url) {
	}

	@Override
	public void afterToUrl(WebDriverEvent event, URL url) {
	}

	@Override
	public void beforeDismiss(WebDriverEvent event) {
	}

	@Override
	public void afterDismiss(WebDriverEvent event) {
	}

	@Override
	public void beforeAccept(WebDriverEvent event) {
	}

	@Override
	public void afterAccept(WebDriverEvent event) {
	}

	@Override
	public void beforeGetTextByAlert(WebDriverEvent event) {
	}

	@Override
	public void afterGetTextByAlert(WebDriverEvent event, String text) {
	}

	@Override
	public void beforeSendKeysByAlert(WebDriverEvent event, String keysToSend) {
	}

	@Override
	public void afterSendKeysByAlert(WebDriverEvent event, String keysToSend) {
	}

	@Override
	public void beforeGetCoordinates(WebDriverEvent event, WebElement element) {
	}

	@Override
	public void afterGetCoordinates(WebDriverEvent event, Coordinates coordinates, WebElement element) {
	}

	@Override
	public <X> void beforeGetScreenshotAsByElement(WebDriverEvent event, OutputType<X> target, WebElement element) {
	}

	@Override
	public <X> void afterGetScreenshotAsByElement(WebDriverEvent event, OutputType<X> target, X screenshot, WebElement element) {
	}

	@Override
	public void beforeUploadFile(WebDriverEvent event, WebElement element, File localFile) {
	}

	@Override
	public void afterUploadFile(WebDriverEvent event, WebElement element, File localFile, String response) {
	}

	@JsonProperty("logEntries")
	@Override
	public List<WebDriverEvent> getListOfEventsRecorded() {
		return Collections.unmodifiableList(logEntries);
	}
	
	@Override
	public String getEventsFormatted() {
		return null;
	}
}
