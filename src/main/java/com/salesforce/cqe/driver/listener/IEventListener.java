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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;

import com.salesforce.cqe.driver.listener.Event.Cmd;

/**
 * Interface which supports registering of a listener with {@link com.salesforce.cqe.driver.EventDispatcher} for logging
 * purposes.
 * 
 * This is an extended version of org.openqa.selenium.support.events.WebDriverEventListener. See
 * https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/support/events/WebDriverEventListener.html
 * for more information.
 * 
 * @since 1.0
 */
public interface IEventListener {
	/**
	 * Location of logfiles produced by Test Drop-in Framework and
	 * its dependent classes: {@value}
	 */
	final String DRILLBIT_LOGFILES_DIR = "target/";

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#close close()}.
	 * @param event
	 *            event record
	 */
	void beforeClose(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#close close()}.
	 * @param event
	 *            event record
	 */
	void afterClose(Event event);

	/**
	 * Called before {@link WebDriver#findElement WebDriver.findElement(...)}.
	 * @param event
	 *            event record
	 * @param by
	 *            locator being used
	 */
	void beforeFindElementByWebDriver(Event event, By by);

	/**
	 * Called after {@link WebDriver#findElement WebDriver.findElement(...)}.
	 * @param event
	 *            event record
	 * @param element
	 *            returned element
	 * @param by
	 *            locator being used
	 */
	void afterFindElementByWebDriver(Event event, WebElement element, By by);

	/**
	 * Called before {@link WebDriver#findElements WebDriver.findElements(...)}.
	 * @param event
	 *            event record
	 * @param by
	 *            locator being used
	 */
	void beforeFindElementsByWebDriver(Event event, By by);

	/**
	 * Called after{@link WebDriver#findElements WebDriver.findElements(...)}.
	 * @param event
	 *            event record
	 * @param elements
	 *            returned list of elements
	 * @param by
	 *            locator being used
	 */
	void afterFindElementsByWebDriver(Event event, List<WebElement> elements, By by);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#get get(String url)}.
	 *
	 * @param event
	 *            event record
	 * @param url
	 *            URL
	 */
	void beforeGet(Event event, String url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#get get(String url)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param url
	 *            URL
	 */
	void afterGet(Event event, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getCurrentUrl getCurrentUrl()}.
	 * @param event
	 *            event record
	 */
	void beforeGetCurrentUrl(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getCurrentUrl getCurrentUrl()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param event
	 *            event record
	 * @param url
	 *            returned URL
	 */
	void afterGetCurrentUrl(Event event, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getPageSource getPageSource()}.
	 * @param event
	 *            event record
	 */
	void beforeGetPageSource(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getPageSource getPageSource()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param event
	 *            event record
	 * @param source
	 *            returned page source
	 */
	void afterGetPageSource(Event event, String source);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getTitle getTitle()}.
	 * @param event
	 *            event record
	 */
	void beforeGetTitle(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getTitle getTitle()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param event
	 *            event record
	 * @param title
	 *            returned page title
	 */
	void afterGetTitle(Event event, String title);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getWindowHandle getWindowHandle()}.
	 * @param event
	 *            event record
	 */
	void beforeGetWindowHandle(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getWindowHandle getWindowHandle()}.
	 *
	 * @param event
	 *            event record
	 * @param handle
	 *            Handle to current window
	 */
	void afterGetWindowHandle(Event event, String handle);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getWindowHandles getWindowHandles()}.
	 * @param event
	 *            event record
	 */
	void beforeGetWindowHandles(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getWindowHandles getWindowHandles()}.
	 * @param event
	 *            event record
	 * @param handles
	 *            Set of handles to windows currently open
	 */
	void afterGetWindowHandles(Event event, Set<String> handles);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#quit quit()}.
	 * @param event
	 *            event record
	 */
	void beforeQuit(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#quit quit()}.
	 * @param event
	 *            event record
	 */
	void afterQuit(Event event);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...) executingAsyncScript(String, Object...)}.
	 * @param event
	 *            event record
	 * @param script
	 *            JavaScript script to execute
	 * @param params
	 *            arguments for script
	 */
	void beforeExecuteAsyncScript(Event event, String script, Map<String, ?> params);

	/**
	 * Called after {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...) executingAsyncScript(String, Object...)}.
	 * @param event
	 *            event record
	 * @param script
	 *            JavaScript script executed
	 * @param params
	 *            arguments for script
	 * @param result
	 *            returned object
	 */
	void afterExecuteAsyncScript(Event event, String script, Map<String, ?> params, Object result);

	/**
	 * Called before {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...) executingScript(String, Object...)}.
	 * @param event
	 *            event record
	 * @param script
	 *            JavaScript script to execute
	 * @param params
	 *            arguments for script
	 */
	void beforeExecuteScript(Event event, String script, Map<String, ?> params);

	/**
	 * Called after {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...) executingScript(String, Object...)}.
	 * @param event
	 *            event record
	 * @param script
	 *            JavaScript script executed
	 * @param params
	 *            arguments for script
	 * @param result
	 *            returned object
	 */
	void afterExecuteScript(Event event, String script, Map<String, ?> params, Object result);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Options object
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) addCookie(Cookie cookie)}.
	 * 
	 * @param event
	 *            event record
	 * @param cookie
	 *            cookie to add
	 */
	void beforeAddCookie(Event event, Cookie cookie);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) addCookie(Cookie cookie)}.
	 * @param event
	 *            event record
	 * @param cookie
	 *            cookie to add
	 */
	void afterAddCookie(Event event, Cookie cookie);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookieNamed(String) deleteCookieNamed(String name)}.
	 * 
	 * @param event
	 *            event record
	 * @param name
	 *            name of cookie to delete
	 */
	void beforeDeleteCookieNamed(Event event, String name);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookieNamed(String) deleteCookieNamed(String name)}.
	 * @param event
	 *            event record
	 * @param name
	 *            name of cookie to delete
	 */
	void afterDeleteCookieNamed(Event event, String name);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookie(Cookie) deleteCookie(Cookie cookie)}.
	 * 
	 * @param event
	 *            event record
	 * @param cookie
	 *            cookie to delete
	 */
	void beforeDeleteCookie(Event event, Cookie cookie);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookie(Cookie) deleteCookie(Cookie cookie)}.
	 * @param event
	 *            event record
	 * @param cookie
	 *            cookie to delete
	 */
	void afterDeleteCookie(Event event, Cookie cookie);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies() deleteAllCookies()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeDeleteAllCookies(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies() deleteAllCookies()}.
	 * @param event
	 *            event record
	 */
	void afterDeleteAllCookies(Event event);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#getCookies() getCookies()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeGetCookies(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#getCookies() getCookies()}.
	 * @param event
	 *            event record
	 * @param cookies
	 *            set of all cookies
	 */
	void afterGetCookies(Event event, Set<Cookie> cookies);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) getCookieNamed(String name)}.
	 * 
	 * @param event
	 *            event record
	 * @param name
	 *            name of cookie to get
	 */
	void beforeGetCookieNamed(Event event, String name);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#getCookieNamed(String) getCookieNamed(String name)}.
	 * @param event
	 *            event record
	 * @param name
	 *            name of cookie to get
	 * @param cookie
	 *            returned cookie
	 */
	void afterGetCookieNamed(Event event, String name, Cookie cookie);
	
	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X> 
	 *            Return type for getScreenshotAs.
	 * @param event
	 *            event record
	 * @param target
	 *            target type, @see OutputType
	 */
	<X> void beforeGetScreenshotAs(Event event, OutputType<X> target);

	/**
	 * Called after {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X> 
	 *            Return type for getScreenshotAs.
	 * @param event
	 *            event record
	 * @param target
	 *            target type, @see OutputType
	 * @param screenshot
	 *            screenshot captured
	 */
	<X> void afterGetScreenshotAs(Event event, OutputType<X> target, X screenshot);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.ImeHandler object
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#getAvailableEngines() getAvailableEngines()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeGetAvailableEngines(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#getAvailableEngines() getAvailableEngines()}.
	 * 
	 * @param event
	 *            event record
	 * @param engines
	 *            list of names of available engines
	 */
	void afterGetAvailableEngines(Event event, List<String> engines);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#getActiveEngine() getActiveEngine()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeGetActiveEngine(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#getActiveEngine() getActiveEngine()}.
	 * 
	 * @param event
	 *            event record
	 * @param engine
	 *            names of active engine
	 */
	void afterGetActiveEngine(Event event, String engine);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#isActivated() isActivated()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeIsActivated(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#isActivated() isActivated()}.
	 * 
	 * @param event
	 *            event record
	 * @param isActive
	 *            state of activation of current engine
	 */
	void afterIsActivated(Event event, boolean isActive);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#deactivate() deactivate()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeDeactivate(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#deactivate() deactivate()}.
	 * 
	 * @param event
	 *            event record
	 */
	void afterDeactivate(Event event);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#activateEngine(String) activateEngine(String engine)}.
	 * 
	 * @param event
	 *            event record
	 * @param engine
	 *            name of engine to activate
	 */
	void beforeActivateEngine(Event event, String engine);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#activateEngine(String) activateEngine(String engine)}.
	 * 
	 * @param event
	 *            event record
	 * @param engine
	 *            name of engine to activate
	 */
	void afterActivateEngine(Event event, String engine);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#back
	 * navigate().back()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeBack(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation
	 * navigate().back()}. Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 */
	void afterBack(Event event);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeForward(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}. Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 */
	void afterForward(Event event);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#refresh
	 * navigate().refresh()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeRefresh(Event event);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#refresh
	 * navigate().refresh()}. Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 */
	void afterRefresh(Event event);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}.
	 * 
	 * @param event
	 *            event record
	 * @param url
	 *            string representation of URL
	 */
	void beforeTo(Event event, String url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}. Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param url
	 *            string representation of URL
	 */
	void afterTo(Event event, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(URL url)}.
	 * 
	 * @param event
	 *            event record
	 * @param url
	 *            URL
	 */
	void beforeToUrl(Event event, URL url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(URL url)}. Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param url
	 *            URL
	 */
	void afterToUrl(Event event, URL url);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Alert object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.Alert#dismiss switchTo().alert().dismiss()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeDismiss(Event event);

	/**
	 * Called after {@link org.openqa.selenium.Alert#dismiss switchTo().alert().dismiss()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 */
	void afterDismiss(Event event);

	/**
	 * Called before {@link org.openqa.selenium.Alert#accept switchTo().alert().accept()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeAccept(Event event);

	/**
	 * Called after {@link org.openqa.selenium.Alert#accept switchTo().alert().accept()}. 
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 */
	void afterAccept(Event event);

	/**
	 * Called before {@link org.openqa.selenium.Alert#getText switchTo().alert().getText()}.
	 * 
	 * @param event
	 *            event record
	 */
	void beforeGetTextByAlert(Event event);

	/**
	 * Called after {@link org.openqa.selenium.Alert#getText switchTo().alert().getText()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param text
	 *            text shown in alert
	 */
	void afterGetTextByAlert(Event event, String text);

	/**
	 * Called before {@link org.openqa.selenium.Alert#sendKeys(String) switchTo().alert().sendKeys(String keysToSend)}.
	 * 
	 * @param event
	 *            event record
	 * @param keysToSend
	 *            keys to enter
	 */
	void beforeSendKeysByAlert(Event event, String keysToSend);

	/**
	 * Called after {@link org.openqa.selenium.Alert#sendKeys(String) switchTo().alert().sendKeys(String keysToSend)}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param keysToSend
	 *            keys to enter
	 */
	void afterSendKeysByAlert(Event event, String keysToSend);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.TargetLocator#activeElement() TargetLocator.activeElement()}.
	 * @param event
	 *            event record
	 */
	void beforeActiveElement(Event event);

	/**
	 * Called after {@link WebDriver.TargetLocator#activeElement() TargetLocator.activeElement()}.
	 * Not called, if an exception is thrown.
	 * @param event event record
	 * @param activeElement the current active WebElement
	 */
	void afterActiveElement(Event event, WebElement activeElement);

	/**
	 * Called before {@link WebDriver.TargetLocator#alert() TargetLocator.alert()}.
	 * @param event
	 *            event record
	 */
	void beforeAlert(Event event);

	/**
	 * Called after {@link WebDriver.TargetLocator#alert() TargetLocator.alert()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param alert
	 *            handle to the Alert
	 */
	void afterAlert(Event event, Alert alert);

	/**
	 * Called before {@link WebDriver.TargetLocator#defaultContent() TargetLocator.defaultContent()}.
	 * @param event
	 *            event record
	 */
	void beforeDefaultContent(Event event);

	/**
	 * Called after {@link WebDriver.TargetLocator#defaultContent() TargetLocator.defaultContent()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 */
	void afterDefaultContent(Event event);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(int) TargetLocator.frame(..)}.
	 * @param event
	 *            event record
	 * @param frameIndex
	 *            0-based index of frame on page
	 */
	void beforeFrameByIndex(Event event, int frameIndex);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(int) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param frameIndex
	 *            0-based index of frame on page
	 */
	void afterFrameByIndex(Event event, int frameIndex);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * @param event
	 *            event record
	 * @param frameName
	 *            name of frame
	 */
	void beforeFrameByName(Event event, String frameName);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param frameName
	 *            name of frame
	 */
	void afterFrameByName(Event event, String frameName);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(WebElement) TargetLocator.frame(..)}.
	 * @param event
	 *            event record
	 * @param frameElement
	 *            element inside frame
	 */
	void beforeFrameByElement(Event event, WebElement frameElement);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param frameElement
	 *            element inside frame
	 */
	void afterFrameByElement(Event event, WebElement frameElement);

	/**
	 * Called before {@link WebDriver.TargetLocator#parentFrame() TargetLocator.parentFrame()}.
	 * @param event
	 *            event record
	 */
	void beforeParentFrame(Event event);

	/**
	 * Called after {@link WebDriver.TargetLocator#parentFrame() TargetLocator.parentFrame()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 */
	void afterParentFrame(Event event);

	/**
	 * Called before {@link WebDriver.TargetLocator#window(java.lang.String) TargetLocator.window(..)}.
	 * @param event
	 *            event record
	 * @param windowName
	 *            name of window
	 */
	void beforeWindow(Event event, String windowName);

	/**
	 * Called after {@link WebDriver.TargetLocator#window(java.lang.String) TargetLocator.window(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param windowName
	 *            name of window
	 */
	void afterWindow(Event event, String windowName);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit) Timeouts.implicitlyWait(..)}.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforeImplicitlyWait(Event event, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit) Timeouts.implicitlyWait(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterImplicitlyWait(Event event, long time, TimeUnit unit);

	/**
	 * Called before {@link WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit) Timeouts.pageLoadTimeout(..)}.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforePageLoadTimeout(Event event, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit) Timeouts.pageLoadTimeout(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterPageLoadTimeout(Event event, long time, TimeUnit unit);

	/**
	 * Called before {@link WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit) Timeouts.setScriptTimeout(..)}.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforeSetScriptTimeout(Event event, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit) Timeouts.setScriptTimeout(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterSetScriptTimeout(Event event, long time, TimeUnit unit);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.Window#fullscreen() Window.fullscreen()}.
	 * @param event
	 *            event record
	 */
	void beforeFullscreen(Event event);

	/**
	 * Called after {@link WebDriver.Window#fullscreen() Window.fullscreen()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 */
	void afterFullscreen(Event event);

	/**
	 * Called before {@link WebDriver.Window#getPosition() getPosition()}.
	 * @param event
	 *            event record
	 */
	void beforeGetPosition(Event event);

	/**
	 * Called after {@link WebDriver.Window#getPosition() getPosition()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param targetPosition
	 *            returned location on screen
	 */
	void afterGetPosition(Event event, Point targetPosition);

	/**
	 * Called before {@link WebDriver.Window#getSize() getSize()}.
	 * @param event
	 *            event record
	 */
	void beforeGetSizeByWindow(Event event);

	/**
	 * Called after {@link WebDriver.Window#getSize() getSize()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param targetSize
	 *            returned window size on screen
	 */
	void afterGetSizeByWindow(Event event, Dimension targetSize);

	/**
	 * Called before {@link WebDriver.Window#maximize() Window.window()}.
	 * @param event
	 *            event record
	 */
	void beforeMaximize(Event event);

	/**
	 * Called after {@link WebDriver.Window#maximize() Window.window()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 */
	void afterMaximize(Event event);

	/**
	 * Called before {@link WebDriver.Window#setPosition(Point) setPosition(..)}.
	 * @param event
	 *            event record
	 * @param targetPosition
	 *            location on screen
	 */
	void beforeSetPosition(Event event, Point targetPosition);

	/**
	 * Called after {@link WebDriver.Window#setPosition(Point) setPosition(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param targetPosition
	 *            location on screen
	 */
	void afterSetPosition(Event event, Point targetPosition);

	/**
	 * Called before {@link WebDriver.Window#setSize(Dimension) setSize(..)}.
	 * @param event
	 *            event record
	 * @param targetSize
	 *            window size on screen
	 */
	void beforeSetSizeByWindow(Event event, Dimension targetSize);

	/**
	 * Called after {@link WebDriver.Window#setSize(Dimension) setSize(..)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param targetSize
	 *            window size on screen
	 */
	void afterSetSizeByWindow(Event event, Dimension targetSize);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebElement#click WebElement.click()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeClick(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#click WebElement.click()}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterClick(Event event, WebElement element);

	/**
	 * Called before {@link WebElement#clear WebElement.clear()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeClear(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#clear WebElement.clear()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterClear(Event event, WebElement element);

	/**
	 * Called before {@link WebElement#findElement WebElement.findElement(...)}.
	 * @param event
	 *            event record
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #beforeFindElementByWebDriver(Event, By) beforeFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void beforeFindElementByElement(Event event, By by, WebElement element);

	/**
	 * Called after {@link WebElement#findElement WebElement.findElement(...)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param returnedElement
	 *            returned element
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #afterFindElementByWebDriver(Event, WebElement, By) afterFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void afterFindElementByElement(Event event, WebElement returnedElement, By by, WebElement element);

	/**
	 * Called before {@link WebElement#findElements WebElement.findElements(...)}.
	 * @param event
	 *            event record
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #beforeFindElementByWebDriver(Event, By) beforeFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void beforeFindElementsByElement(Event event, By by, WebElement element);

	/**
	 * Called after {@link WebElement#findElements WebElement.findElements(...)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param returnedElements
	 *            returned list of elements
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #afterFindElementByWebDriver(Event, WebElement, By) afterFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void afterFindElementsByElement(Event event, List<WebElement> returnedElements, By by, WebElement element);

	/**
	 * Called before {@link WebElement#getAttribute WebElement.getAttribute(...)}.
	 * @param event
	 *            event record
	 * @param name
	 *            name of the attribute to get
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetAttribute(Event event, String name, WebElement element);

	/**
	 * Called after {@link WebElement#getAttribute WebElement.getAttribute(...)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param value
	 *            value of the named attribute
	 * @param name
	 *            name of the attribute to get
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetAttribute(Event event, String value, String name, WebElement element);

	/**
	 * Called before {@link WebElement#getCssValue WebElement.getCssValue()}.
	 * @param event
	 *            event record
	 * @param propertyName
	 * 			  name of the CSS property to get the value of 
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetCssValue(Event event, String propertyName, WebElement element);

	/**
	 * Called after {@link WebElement#getCssValue WebElement.getCssValue()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param propertyName
	 * 			  name of the CSS property to get the value of 
	 * @param value
	 *            the retrieved CSS value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetCssValue(Event event, String propertyName, String value, WebElement element);

	/**
	 * Called before {@link WebElement#getTagName WebElement.getTagName()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetTagName(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#getTagName WebElement.getTagName()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param tagName
	 *            the retrieved tag name
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetTagName(Event event, String tagName, WebElement element);

	/**
	 * Called before {@link WebElement#getText WebElement.getText()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetText(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#getText WebElement.getText()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param text
	 *            the retrieved text
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetText(Event event, String text, WebElement element);

	/**
	 * Called before {@link WebElement#isDisplayed WebElement.isDisplayed()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsDisplayed(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#isDisplayed WebElement.isDisplayed()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param isDisplayed
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsDisplayed(Event event, boolean isDisplayed, WebElement element);

	/**
	 * Called before {@link WebElement#isEnabled WebElement.isEnabled()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsEnabled(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#isEnabled WebElement.isEnabled()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param isEnabled
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsEnabled(Event event, boolean isEnabled, WebElement element);

	/**
	 * Called before {@link WebElement#isSelected WebElement.isSelected()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsSelected(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#isSelected WebElement.isSelected()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param isSelected
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsSelected(Event event, boolean isSelected, WebElement element);
	
	/**
	 * Called before {@link WebElement#getLocation WebElement.getLocation()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetLocation(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#getLocation WebElement.getLocation()}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param point
	 *            the retrieved point
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetLocation(Event event, Point point, WebElement element);
	
	/**
	 * Called before {@link WebElement#getSize WebElement.getSize()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetSizeByElement(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#getSize WebElement.getSize()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param dimension
	 *            the retrieved dimension
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetSizeByElement(Event event, Dimension dimension, WebElement element);

	/**
	 * Called before {@link WebElement#getRect WebElement.getRect()}.
	 * 
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetRect(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#getRect WebElement.getRect()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param rectangle
	 *            the retrieved rectangle
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetRect(Event event, Rectangle rectangle, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Locatable#getCoordinates getCoordinates()}.
	 * 
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetCoordinates(Event event, WebElement element);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Locatable#getCoordinates getCoordinates()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param event
	 *            event record
	 * @param coordinates
	 *            the retrieved coordinates
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetCoordinates(Event event, Coordinates coordinates, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X>
	 *            Return type for getScreenshotAs.
	 * @param event
	 *            event record
	 * @param target
	 *            target type, @see OutputType
	 * @param element
	 *            the WebElement being used for the action
	 */
	<X> void beforeGetScreenshotAsByElement(Event event, OutputType<X> target, WebElement element);

	/**
	 * Called after {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X>
	 *            Return type for getScreenshotAs.
	 * @param event
	 *            event record
	 * @param target
	 *            target type, @see OutputType
	 * @param screenshot
	 *            screenshot captured
	 * @param element
	 *            the WebElement being used for the action
	 */
	<X> void afterGetScreenshotAsByElement(Event event, OutputType<X> target, X screenshot, WebElement element);

	/**
	 * Called before {@link WebElement#sendKeys WebElement.sendKeys(...)}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 * @param keysToSend
	 *            text to insert
	 */
	void beforeSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend);

	/**
	 * Called after {@link WebElement#sendKeys WebElement.sendKeys(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 * @param keysToSend
	 *            text to insert
	 */
	void afterSendKeysByElement(Event event, WebElement element, CharSequence... keysToSend);

	/**
	 * Called before {@link WebElement#sendKeys WebElement.sendKeys(...)} if the keys to send
	 * are the name of a local file.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 * @param localFile
	 *            local file to upload
	 */
	void beforeUploadFile(Event event, WebElement element, File localFile);

	/**
	 * Called after {@link WebElement#sendKeys WebElement.sendKeys(...)}} if the keys to send
	 * are the name of a local file. Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 * @param localFile
	 *            local file to upload
	 * @param response
	 *            response to file upload
	 */
	void afterUploadFile(Event event, WebElement element, File localFile, String response);
	
	/**
	 * Called before {@link WebElement#submit WebElement.submit()}.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeSubmit(Event event, WebElement element);

	/**
	 * Called after {@link WebElement#submit WebElement.submit()}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterSubmit(Event event, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#sendKeys Keyboard.sendKeys(...)}.
	 * @param event
	 *            event record
	 * @param keysToSend
	 *            text to insert
	 */
	void beforeSendKeysByKeyboard(Event event, CharSequence... keysToSend);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#sendKeys Keyboard.sendKeys(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param keysToSend
	 *            text to insert
	 */
	void afterSendKeysByKeyboard(Event event, CharSequence... keysToSend);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#pressKey Keyboard.pressKey(...)}.
	 * @param event
	 *            event record
	 * @param keyToPress
	 *            key to press
	 */
	void beforePressKey(Event event, CharSequence... keyToPress);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#pressKey Keyboard.pressKey(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param keyToPress
	 *            key to press
	 */
	void afterPressKey(Event event, CharSequence... keyToPress);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#releaseKey Keyboard.releaseKey(...)}.
	 * @param event
	 *            event record
	 * @param keyToRelease
	 *            key to release
	 */
	void beforeReleaseKey(Event event, CharSequence... keyToRelease);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#releaseKey Keyboard.releaseKey(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param keyToRelease
	 *            key to release
	 */
	void afterReleaseKey(Event event, CharSequence... keyToRelease);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#click Mouse.click(...)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where click is performed
	 */
	void beforeClickByMouse(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#click Mouse.click(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where click is performed
	 */
	void afterClickByMouse(Event event, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#doubleClick Mouse.doubleClick(...)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where double click is performed
	 */
	void beforeDoubleClick(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#doubleClick Mouse.doubleClick(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where double click is performed
	 */
	void afterDoubleClick(Event event, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseDown Mouse.mouseDown(...)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse down is performed
	 */
	void beforeMouseDown(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseDown Mouse.mouseDown(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse down is performed
	 */
	void afterMouseDown(Event event, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseUp Mouse.mouseUp(...)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse up is performed
	 */
	void beforeMouseUp(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseUp Mouse.mouseUp(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse up is performed
	 */
	void afterMouseUp(Event event, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates) Mouse.mouseMove(Coordinates where)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse is moved to
	 */
	void beforeMouseMove(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates) Mouse.mouseMove(Coordinates where)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse is moved to
	 */
	void afterMouseMove(Event event, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates,long,long) Mouse.mouseMove(Coordinates where, longxOffset, long yOffset)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse is moved to
	 * @param xOffset
	 *            offset in x direction
	 * @param yOffset
	 *            offset in y direction
	 */
	void beforeMouseMove(Event event, Coordinates where, long xOffset, long yOffset);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates,long,long) Mouse.mouseMove(Coordinates where, longxOffset, long yOffset)}.
	 * Not called, if an exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where mouse is moved to
	 * @param xOffset
	 *            offset in x direction
	 * @param yOffset
	 *            offset in y direction
	 */
	void afterMouseMove(Event event, Coordinates where, long xOffset, long yOffset);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#contextClick Mouse.contextClick(...)}.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where context click is performed
	 */
	void beforeContextClick(Event event, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#contextClick Mouse.contextClick(...)}}. Not called, if an
	 * exception is thrown.
	 * @param event
	 *            event record
	 * @param where
	 *            coordinates where context click is performed
	 */
	void afterContextClick(Event event, Coordinates where);

	/**
	 * Called whenever a command throws an exception.
	 * @param event
	 *            event record
	 * @param cmd
	 *            the command which ran into an issue
	 * @param throwable
	 *            the exception that will be thrown
	 */
	void onException(Event event, Cmd cmd, Throwable throwable);
	
	/**
	 * Gets the list of events logged so far by the implementing listener.
	 * 
	 * Preferably it should return an immutable list by wrapping the
	 * list-to-be-returned with {@link Collections#unmodifiableList(List)} so that
	 * multiple calls of this method are allowed.
	 * 
	 * @return list of events logged so far
	 */
	List<Event> getListOfEventsRecorded();
	
	/**
	 * Gets the events logged so far by the implementing listener as a string
	 * or NULL, if this makes no sense.
	 * 
	 * The event listener can present the logged events in a formatted style,
	 * ready to written to disk as-is or deny provisioning this output by
	 * returning NULL.
	 * 
	 * @return events logged so far as string or NULL
	 */
	String getEventsFormatted();
}
