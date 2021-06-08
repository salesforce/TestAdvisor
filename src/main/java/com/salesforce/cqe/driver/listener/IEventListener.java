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

import com.salesforce.cqe.driver.listener.Step.Cmd;

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
	 * @param step
	 *            step record
	 */
	void beforeClose(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#close close()}.
	 * @param step
	 *            step record
	 */
	void afterClose(Step step);

	/**
	 * Called before {@link WebDriver#findElement WebDriver.findElement(...)}.
	 * @param step
	 *            step record
	 * @param by
	 *            locator being used
	 */
	void beforeFindElementByWebDriver(Step step, By by);

	/**
	 * Called after {@link WebDriver#findElement WebDriver.findElement(...)}.
	 * @param step
	 *            step record
	 * @param element
	 *            returned element
	 * @param by
	 *            locator being used
	 */
	void afterFindElementByWebDriver(Step step, WebElement element, By by);

	/**
	 * Called before {@link WebDriver#findElements WebDriver.findElements(...)}.
	 * @param step
	 *            step record
	 * @param by
	 *            locator being used
	 */
	void beforeFindElementsByWebDriver(Step step, By by);

	/**
	 * Called after{@link WebDriver#findElements WebDriver.findElements(...)}.
	 * @param step
	 *            step record
	 * @param elements
	 *            returned list of elements
	 * @param by
	 *            locator being used
	 */
	void afterFindElementsByWebDriver(Step step, List<WebElement> elements, By by);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#get get(String url)}.
	 *
	 * @param step
	 *            step record
	 * @param url
	 *            URL
	 */
	void beforeGet(Step step, String url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#get get(String url)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param url
	 *            URL
	 */
	void afterGet(Step step, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getCurrentUrl getCurrentUrl()}.
	 * @param step
	 *            step record
	 */
	void beforeGetCurrentUrl(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getCurrentUrl getCurrentUrl()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param step
	 *            step record
	 * @param url
	 *            returned URL
	 */
	void afterGetCurrentUrl(Step step, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getPageSource getPageSource()}.
	 * @param step
	 *            step record
	 */
	void beforeGetPageSource(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getPageSource getPageSource()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param step
	 *            step record
	 * @param source
	 *            returned page source
	 */
	void afterGetPageSource(Step step, String source);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getTitle getTitle()}.
	 * @param step
	 *            step record
	 */
	void beforeGetTitle(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getTitle getTitle()}.
	 * Not called, if an exception is thrown.
	 *
	 * @param step
	 *            step record
	 * @param title
	 *            returned page title
	 */
	void afterGetTitle(Step step, String title);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getWindowHandle getWindowHandle()}.
	 * @param step
	 *            step record
	 */
	void beforeGetWindowHandle(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getWindowHandle getWindowHandle()}.
	 *
	 * @param step
	 *            step record
	 * @param handle
	 *            Handle to current window
	 */
	void afterGetWindowHandle(Step step, String handle);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#getWindowHandles getWindowHandles()}.
	 * @param step
	 *            step record
	 */
	void beforeGetWindowHandles(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#getWindowHandles getWindowHandles()}.
	 * @param step
	 *            step record
	 * @param handles
	 *            Set of handles to windows currently open
	 */
	void afterGetWindowHandles(Step step, Set<String> handles);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver#quit quit()}.
	 * @param step
	 *            step record
	 */
	void beforeQuit(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver#quit quit()}.
	 * @param step
	 *            step record
	 */
	void afterQuit(Step step);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...) executingAsyncScript(String, Object...)}.
	 * @param step
	 *            step record
	 * @param script
	 *            JavaScript script to execute
	 * @param params
	 *            arguments for script
	 */
	void beforeExecuteAsyncScript(Step step, String script, Map<String, ?> params);

	/**
	 * Called after {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...) executingAsyncScript(String, Object...)}.
	 * @param step
	 *            step record
	 * @param script
	 *            JavaScript script executed
	 * @param params
	 *            arguments for script
	 * @param result
	 *            returned object
	 */
	void afterExecuteAsyncScript(Step step, String script, Map<String, ?> params, Object result);

	/**
	 * Called before {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...) executingScript(String, Object...)}.
	 * @param step
	 *            step record
	 * @param script
	 *            JavaScript script to execute
	 * @param params
	 *            arguments for script
	 */
	void beforeExecuteScript(Step step, String script, Map<String, ?> params);

	/**
	 * Called after {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...) executingScript(String, Object...)}.
	 * @param step
	 *            step record
	 * @param script
	 *            JavaScript script executed
	 * @param params
	 *            arguments for script
	 * @param result
	 *            returned object
	 */
	void afterExecuteScript(Step step, String script, Map<String, ?> params, Object result);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Options object
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) addCookie(Cookie cookie)}.
	 * 
	 * @param step
	 *            step record
	 * @param cookie
	 *            cookie to add
	 */
	void beforeAddCookie(Step step, Cookie cookie);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) addCookie(Cookie cookie)}.
	 * @param step
	 *            step record
	 * @param cookie
	 *            cookie to add
	 */
	void afterAddCookie(Step step, Cookie cookie);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookieNamed(String) deleteCookieNamed(String name)}.
	 * 
	 * @param step
	 *            step record
	 * @param name
	 *            name of cookie to delete
	 */
	void beforeDeleteCookieNamed(Step step, String name);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookieNamed(String) deleteCookieNamed(String name)}.
	 * @param step
	 *            step record
	 * @param name
	 *            name of cookie to delete
	 */
	void afterDeleteCookieNamed(Step step, String name);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteCookie(Cookie) deleteCookie(Cookie cookie)}.
	 * 
	 * @param step
	 *            step record
	 * @param cookie
	 *            cookie to delete
	 */
	void beforeDeleteCookie(Step step, Cookie cookie);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteCookie(Cookie) deleteCookie(Cookie cookie)}.
	 * @param step
	 *            step record
	 * @param cookie
	 *            cookie to delete
	 */
	void afterDeleteCookie(Step step, Cookie cookie);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies() deleteAllCookies()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeDeleteAllCookies(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#deleteAllCookies() deleteAllCookies()}.
	 * @param step
	 *            step record
	 */
	void afterDeleteAllCookies(Step step);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#getCookies() getCookies()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeGetCookies(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#getCookies() getCookies()}.
	 * @param step
	 *            step record
	 * @param cookies
	 *            set of all cookies
	 */
	void afterGetCookies(Step step, Set<Cookie> cookies);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Options#addCookie(Cookie) getCookieNamed(String name)}.
	 * 
	 * @param step
	 *            step record
	 * @param name
	 *            name of cookie to get
	 */
	void beforeGetCookieNamed(Step step, String name);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Options#getCookieNamed(String) getCookieNamed(String name)}.
	 * @param step
	 *            step record
	 * @param name
	 *            name of cookie to get
	 * @param cookie
	 *            returned cookie
	 */
	void afterGetCookieNamed(Step step, String name, Cookie cookie);
	
	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to TakesScreenshot.
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X> 
	 *            Return type for getScreenshotAs.
	 * @param step
	 *            step record
	 * @param target
	 *            target type, @see OutputType
	 */
	<X> void beforeGetScreenshotAs(Step step, OutputType<X> target);

	/**
	 * Called after {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X> 
	 *            Return type for getScreenshotAs.
	 * @param step
	 *            step record
	 * @param target
	 *            target type, @see OutputType
	 * @param screenshot
	 *            screenshot captured
	 */
	<X> void afterGetScreenshotAs(Step step, OutputType<X> target, X screenshot);

	/*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.ImeHandler object
	 *--------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#getAvailableEngines() getAvailableEngines()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeGetAvailableEngines(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#getAvailableEngines() getAvailableEngines()}.
	 * 
	 * @param step
	 *            step record
	 * @param engines
	 *            list of names of available engines
	 */
	void afterGetAvailableEngines(Step step, List<String> engines);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#getActiveEngine() getActiveEngine()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeGetActiveEngine(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#getActiveEngine() getActiveEngine()}.
	 * 
	 * @param step
	 *            step record
	 * @param engine
	 *            names of active engine
	 */
	void afterGetActiveEngine(Step step, String engine);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#isActivated() isActivated()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeIsActivated(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#isActivated() isActivated()}.
	 * 
	 * @param step
	 *            step record
	 * @param isActive
	 *            state of activation of current engine
	 */
	void afterIsActivated(Step step, boolean isActive);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#deactivate() deactivate()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeDeactivate(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#deactivate() deactivate()}.
	 * 
	 * @param step
	 *            step record
	 */
	void afterDeactivate(Step step);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.ImeHandler#activateEngine(String) activateEngine(String engine)}.
	 * 
	 * @param step
	 *            step record
	 * @param engine
	 *            name of engine to activate
	 */
	void beforeActivateEngine(Step step, String engine);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.ImeHandler#activateEngine(String) activateEngine(String engine)}.
	 * 
	 * @param step
	 *            step record
	 * @param engine
	 *            name of engine to activate
	 */
	void afterActivateEngine(Step step, String engine);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#back
	 * navigate().back()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeBack(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation
	 * navigate().back()}. Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 */
	void afterBack(Step step);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeForward(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward
	 * navigate().forward()}. Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 */
	void afterForward(Step step);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#refresh
	 * navigate().refresh()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeRefresh(Step step);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#refresh
	 * navigate().refresh()}. Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 */
	void afterRefresh(Step step);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}.
	 * 
	 * @param step
	 *            step record
	 * @param url
	 *            string representation of URL
	 */
	void beforeTo(Step step, String url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(String url)}. Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param url
	 *            string representation of URL
	 */
	void afterTo(Step step, String url);

	/**
	 * Called before {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(URL url)}.
	 * 
	 * @param step
	 *            step record
	 * @param url
	 *            URL
	 */
	void beforeToUrl(Step step, URL url);

	/**
	 * Called after {@link org.openqa.selenium.WebDriver.Navigation#to
	 * navigate().to(URL url)}. Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param url
	 *            URL
	 */
	void afterToUrl(Step step, URL url);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Alert object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link org.openqa.selenium.Alert#dismiss switchTo().alert().dismiss()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeDismiss(Step step);

	/**
	 * Called after {@link org.openqa.selenium.Alert#dismiss switchTo().alert().dismiss()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 */
	void afterDismiss(Step step);

	/**
	 * Called before {@link org.openqa.selenium.Alert#accept switchTo().alert().accept()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeAccept(Step step);

	/**
	 * Called after {@link org.openqa.selenium.Alert#accept switchTo().alert().accept()}. 
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 */
	void afterAccept(Step step);

	/**
	 * Called before {@link org.openqa.selenium.Alert#getText switchTo().alert().getText()}.
	 * 
	 * @param step
	 *            step record
	 */
	void beforeGetTextByAlert(Step step);

	/**
	 * Called after {@link org.openqa.selenium.Alert#getText switchTo().alert().getText()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param text
	 *            text shown in alert
	 */
	void afterGetTextByAlert(Step step, String text);

	/**
	 * Called before {@link org.openqa.selenium.Alert#sendKeys(String) switchTo().alert().sendKeys(String keysToSend)}.
	 * 
	 * @param step
	 *            step record
	 * @param keysToSend
	 *            keys to enter
	 */
	void beforeSendKeysByAlert(Step step, String keysToSend);

	/**
	 * Called after {@link org.openqa.selenium.Alert#sendKeys(String) switchTo().alert().sendKeys(String keysToSend)}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param keysToSend
	 *            keys to enter
	 */
	void afterSendKeysByAlert(Step step, String keysToSend);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.TargetLocator object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.TargetLocator#activeElement() TargetLocator.activeElement()}.
	 * @param step
	 *            step record
	 */
	void beforeActiveElement(Step step);

	/**
	 * Called after {@link WebDriver.TargetLocator#activeElement() TargetLocator.activeElement()}.
	 * Not called, if an exception is thrown.
	 * @param step step record
	 * @param activeElement the current active WebElement
	 */
	void afterActiveElement(Step step, WebElement activeElement);

	/**
	 * Called before {@link WebDriver.TargetLocator#alert() TargetLocator.alert()}.
	 * @param step
	 *            step record
	 */
	void beforeAlert(Step step);

	/**
	 * Called after {@link WebDriver.TargetLocator#alert() TargetLocator.alert()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param alert
	 *            handle to the Alert
	 */
	void afterAlert(Step step, Alert alert);

	/**
	 * Called before {@link WebDriver.TargetLocator#defaultContent() TargetLocator.defaultContent()}.
	 * @param step
	 *            step record
	 */
	void beforeDefaultContent(Step step);

	/**
	 * Called after {@link WebDriver.TargetLocator#defaultContent() TargetLocator.defaultContent()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 */
	void afterDefaultContent(Step step);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(int) TargetLocator.frame(..)}.
	 * @param step
	 *            step record
	 * @param frameIndex
	 *            0-based index of frame on page
	 */
	void beforeFrameByIndex(Step step, int frameIndex);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(int) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param frameIndex
	 *            0-based index of frame on page
	 */
	void afterFrameByIndex(Step step, int frameIndex);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * @param step
	 *            step record
	 * @param frameName
	 *            name of frame
	 */
	void beforeFrameByName(Step step, String frameName);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param frameName
	 *            name of frame
	 */
	void afterFrameByName(Step step, String frameName);

	/**
	 * Called before {@link WebDriver.TargetLocator#frame(WebElement) TargetLocator.frame(..)}.
	 * @param step
	 *            step record
	 * @param frameElement
	 *            element inside frame
	 */
	void beforeFrameByElement(Step step, WebElement frameElement);

	/**
	 * Called after {@link WebDriver.TargetLocator#frame(java.lang.String) TargetLocator.frame(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param frameElement
	 *            element inside frame
	 */
	void afterFrameByElement(Step step, WebElement frameElement);

	/**
	 * Called before {@link WebDriver.TargetLocator#parentFrame() TargetLocator.parentFrame()}.
	 * @param step
	 *            step record
	 */
	void beforeParentFrame(Step step);

	/**
	 * Called after {@link WebDriver.TargetLocator#parentFrame() TargetLocator.parentFrame()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 */
	void afterParentFrame(Step step);

	/**
	 * Called before {@link WebDriver.TargetLocator#window(java.lang.String) TargetLocator.window(..)}.
	 * @param step
	 *            step record
	 * @param windowName
	 *            name of window
	 */
	void beforeWindow(Step step, String windowName);

	/**
	 * Called after {@link WebDriver.TargetLocator#window(java.lang.String) TargetLocator.window(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param windowName
	 *            name of window
	 */
	void afterWindow(Step step, String windowName);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Timeouts object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit) Timeouts.implicitlyWait(..)}.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforeImplicitlyWait(Step step, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#implicitlyWait(long, java.util.concurrent.TimeUnit) Timeouts.implicitlyWait(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterImplicitlyWait(Step step, long time, TimeUnit unit);

	/**
	 * Called before {@link WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit) Timeouts.pageLoadTimeout(..)}.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforePageLoadTimeout(Step step, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#pageLoadTimeout(long, java.util.concurrent.TimeUnit) Timeouts.pageLoadTimeout(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterPageLoadTimeout(Step step, long time, TimeUnit unit);

	/**
	 * Called before {@link WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit) Timeouts.setScriptTimeout(..)}.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void beforeSetScriptTimeout(Step step, long time, TimeUnit unit);

	/**
	 * Called after {@link WebDriver.Timeouts#setScriptTimeout(long, java.util.concurrent.TimeUnit) Timeouts.setScriptTimeout(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param time
	 *            time to wait; to be converted using the given time unit
	 * @param unit
	 *            time unit to use to convert the given time value
	 */
	void afterSetScriptTimeout(Step step, long time, TimeUnit unit);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebDriver.Window#fullscreen() Window.fullscreen()}.
	 * @param step
	 *            step record
	 */
	void beforeFullscreen(Step step);

	/**
	 * Called after {@link WebDriver.Window#fullscreen() Window.fullscreen()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 */
	void afterFullscreen(Step step);

	/**
	 * Called before {@link WebDriver.Window#getPosition() getPosition()}.
	 * @param step
	 *            step record
	 */
	void beforeGetPosition(Step step);

	/**
	 * Called after {@link WebDriver.Window#getPosition() getPosition()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param targetPosition
	 *            returned location on screen
	 */
	void afterGetPosition(Step step, Point targetPosition);

	/**
	 * Called before {@link WebDriver.Window#getSize() getSize()}.
	 * @param step
	 *            step record
	 */
	void beforeGetSizeByWindow(Step step);

	/**
	 * Called after {@link WebDriver.Window#getSize() getSize()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param targetSize
	 *            returned window size on screen
	 */
	void afterGetSizeByWindow(Step step, Dimension targetSize);

	/**
	 * Called before {@link WebDriver.Window#maximize() Window.window()}.
	 * @param step
	 *            step record
	 */
	void beforeMaximize(Step step);

	/**
	 * Called after {@link WebDriver.Window#maximize() Window.window()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 */
	void afterMaximize(Step step);

	/**
	 * Called before {@link WebDriver.Window#setPosition(Point) setPosition(..)}.
	 * @param step
	 *            step record
	 * @param targetPosition
	 *            location on screen
	 */
	void beforeSetPosition(Step step, Point targetPosition);

	/**
	 * Called after {@link WebDriver.Window#setPosition(Point) setPosition(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param targetPosition
	 *            location on screen
	 */
	void afterSetPosition(Step step, Point targetPosition);

	/**
	 * Called before {@link WebDriver.Window#setSize(Dimension) setSize(..)}.
	 * @param step
	 *            step record
	 * @param targetSize
	 *            window size on screen
	 */
	void beforeSetSizeByWindow(Step step, Dimension targetSize);

	/**
	 * Called after {@link WebDriver.Window#setSize(Dimension) setSize(..)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param targetSize
	 *            window size on screen
	 */
	void afterSetSizeByWindow(Step step, Dimension targetSize);

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	/**
	 * Called before {@link WebElement#click WebElement.click()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeClick(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#click WebElement.click()}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterClick(Step step, WebElement element);

	/**
	 * Called before {@link WebElement#clear WebElement.clear()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeClear(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#clear WebElement.clear()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterClear(Step step, WebElement element);

	/**
	 * Called before {@link WebElement#findElement WebElement.findElement(...)}.
	 * @param step
	 *            step record
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #beforeFindElementByWebDriver(Step, By) beforeFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void beforeFindElementByElement(Step step, By by, WebElement element);

	/**
	 * Called after {@link WebElement#findElement WebElement.findElement(...)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param returnedElement
	 *            returned element
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #afterFindElementByWebDriver(Step, WebElement, By) afterFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void afterFindElementByElement(Step step, WebElement returnedElement, By by, WebElement element);

	/**
	 * Called before {@link WebElement#findElements WebElement.findElements(...)}.
	 * @param step
	 *            step record
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #beforeFindElementByWebDriver(Step, By) beforeFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void beforeFindElementsByElement(Step step, By by, WebElement element);

	/**
	 * Called after {@link WebElement#findElements WebElement.findElements(...)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param returnedElements
	 *            returned list of elements
	 * @param by
	 *            locator being used
	 * @param element
	 *            use {@link #afterFindElementByWebDriver(Step, WebElement, By) afterFindElement(By, WebDriver)} if a find method of
	 *            <code>WebDriver</code> is called.
	 */
	void afterFindElementsByElement(Step step, List<WebElement> returnedElements, By by, WebElement element);

	/**
	 * Called before {@link WebElement#getAttribute WebElement.getAttribute(...)}.
	 * @param step
	 *            step record
	 * @param name
	 *            name of the attribute to get
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetAttribute(Step step, String name, WebElement element);

	/**
	 * Called after {@link WebElement#getAttribute WebElement.getAttribute(...)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param value
	 *            value of the named attribute
	 * @param name
	 *            name of the attribute to get
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetAttribute(Step step, String value, String name, WebElement element);

	/**
	 * Called before {@link WebElement#getCssValue WebElement.getCssValue()}.
	 * @param step
	 *            step record
	 * @param propertyName
	 * 			  name of the CSS property to get the value of 
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetCssValue(Step step, String propertyName, WebElement element);

	/**
	 * Called after {@link WebElement#getCssValue WebElement.getCssValue()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param propertyName
	 * 			  name of the CSS property to get the value of 
	 * @param value
	 *            the retrieved CSS value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetCssValue(Step step, String propertyName, String value, WebElement element);

	/**
	 * Called before {@link WebElement#getTagName WebElement.getTagName()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetTagName(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#getTagName WebElement.getTagName()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param tagName
	 *            the retrieved tag name
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetTagName(Step step, String tagName, WebElement element);

	/**
	 * Called before {@link WebElement#getText WebElement.getText()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetText(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#getText WebElement.getText()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param text
	 *            the retrieved text
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetText(Step step, String text, WebElement element);

	/**
	 * Called before {@link WebElement#isDisplayed WebElement.isDisplayed()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsDisplayed(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#isDisplayed WebElement.isDisplayed()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param isDisplayed
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsDisplayed(Step step, boolean isDisplayed, WebElement element);

	/**
	 * Called before {@link WebElement#isEnabled WebElement.isEnabled()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsEnabled(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#isEnabled WebElement.isEnabled()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param isEnabled
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsEnabled(Step step, boolean isEnabled, WebElement element);

	/**
	 * Called before {@link WebElement#isSelected WebElement.isSelected()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeIsSelected(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#isSelected WebElement.isSelected()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param isSelected
	 *            the retrieved value
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterIsSelected(Step step, boolean isSelected, WebElement element);
	
	/**
	 * Called before {@link WebElement#getLocation WebElement.getLocation()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetLocation(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#getLocation WebElement.getLocation()}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param point
	 *            the retrieved point
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetLocation(Step step, Point point, WebElement element);
	
	/**
	 * Called before {@link WebElement#getSize WebElement.getSize()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetSizeByElement(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#getSize WebElement.getSize()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param dimension
	 *            the retrieved dimension
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetSizeByElement(Step step, Dimension dimension, WebElement element);

	/**
	 * Called before {@link WebElement#getRect WebElement.getRect()}.
	 * 
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetRect(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#getRect WebElement.getRect()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param rectangle
	 *            the retrieved rectangle
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetRect(Step step, Rectangle rectangle, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Locatable#getCoordinates getCoordinates()}.
	 * 
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeGetCoordinates(Step step, WebElement element);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Locatable#getCoordinates getCoordinates()}.
	 * Not called, if an exception is thrown.
	 * 
	 * @param step
	 *            step record
	 * @param coordinates
	 *            the retrieved coordinates
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterGetCoordinates(Step step, Coordinates coordinates, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X>
	 *            Return type for getScreenshotAs.
	 * @param step
	 *            step record
	 * @param target
	 *            target type, @see OutputType
	 * @param element
	 *            the WebElement being used for the action
	 */
	<X> void beforeGetScreenshotAsByElement(Step step, OutputType<X> target, WebElement element);

	/**
	 * Called after {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType target) getScreenshotAs(OutputType&lt;X&gt; target)}.
	 * 
	 * @param <X>
	 *            Return type for getScreenshotAs.
	 * @param step
	 *            step record
	 * @param target
	 *            target type, @see OutputType
	 * @param screenshot
	 *            screenshot captured
	 * @param element
	 *            the WebElement being used for the action
	 */
	<X> void afterGetScreenshotAsByElement(Step step, OutputType<X> target, X screenshot, WebElement element);

	/**
	 * Called before {@link WebElement#sendKeys WebElement.sendKeys(...)}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 * @param keysToSend
	 *            text to insert
	 */
	void beforeSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend);

	/**
	 * Called after {@link WebElement#sendKeys WebElement.sendKeys(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 * @param keysToSend
	 *            text to insert
	 */
	void afterSendKeysByElement(Step step, WebElement element, CharSequence... keysToSend);

	/**
	 * Called before {@link WebElement#sendKeys WebElement.sendKeys(...)} if the keys to send
	 * are the name of a local file.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 * @param localFile
	 *            local file to upload
	 */
	void beforeUploadFile(Step step, WebElement element, File localFile);

	/**
	 * Called after {@link WebElement#sendKeys WebElement.sendKeys(...)}} if the keys to send
	 * are the name of a local file. Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 * @param localFile
	 *            local file to upload
	 * @param response
	 *            response to file upload
	 */
	void afterUploadFile(Step step, WebElement element, File localFile, String response);
	
	/**
	 * Called before {@link WebElement#submit WebElement.submit()}.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void beforeSubmit(Step step, WebElement element);

	/**
	 * Called after {@link WebElement#submit WebElement.submit()}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param element
	 *            the WebElement being used for the action
	 */
	void afterSubmit(Step step, WebElement element);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#sendKeys Keyboard.sendKeys(...)}.
	 * @param step
	 *            step record
	 * @param keysToSend
	 *            text to insert
	 */
	void beforeSendKeysByKeyboard(Step step, CharSequence... keysToSend);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#sendKeys Keyboard.sendKeys(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param keysToSend
	 *            text to insert
	 */
	void afterSendKeysByKeyboard(Step step, CharSequence... keysToSend);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#pressKey Keyboard.pressKey(...)}.
	 * @param step
	 *            step record
	 * @param keyToPress
	 *            key to press
	 */
	void beforePressKey(Step step, CharSequence... keyToPress);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#pressKey Keyboard.pressKey(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param keyToPress
	 *            key to press
	 */
	void afterPressKey(Step step, CharSequence... keyToPress);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Keyboard#releaseKey Keyboard.releaseKey(...)}.
	 * @param step
	 *            step record
	 * @param keyToRelease
	 *            key to release
	 */
	void beforeReleaseKey(Step step, CharSequence... keyToRelease);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Keyboard#releaseKey Keyboard.releaseKey(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param keyToRelease
	 *            key to release
	 */
	void afterReleaseKey(Step step, CharSequence... keyToRelease);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#click Mouse.click(...)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where click is performed
	 */
	void beforeClickByMouse(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#click Mouse.click(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where click is performed
	 */
	void afterClickByMouse(Step step, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#doubleClick Mouse.doubleClick(...)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where double click is performed
	 */
	void beforeDoubleClick(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#doubleClick Mouse.doubleClick(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where double click is performed
	 */
	void afterDoubleClick(Step step, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseDown Mouse.mouseDown(...)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse down is performed
	 */
	void beforeMouseDown(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseDown Mouse.mouseDown(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse down is performed
	 */
	void afterMouseDown(Step step, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseUp Mouse.mouseUp(...)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse up is performed
	 */
	void beforeMouseUp(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseUp Mouse.mouseUp(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse up is performed
	 */
	void afterMouseUp(Step step, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates) Mouse.mouseMove(Coordinates where)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse is moved to
	 */
	void beforeMouseMove(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates) Mouse.mouseMove(Coordinates where)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse is moved to
	 */
	void afterMouseMove(Step step, Coordinates where);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates,long,long) Mouse.mouseMove(Coordinates where, longxOffset, long yOffset)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse is moved to
	 * @param xOffset
	 *            offset in x direction
	 * @param yOffset
	 *            offset in y direction
	 */
	void beforeMouseMove(Step step, Coordinates where, long xOffset, long yOffset);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#mouseMove(Coordinates,long,long) Mouse.mouseMove(Coordinates where, longxOffset, long yOffset)}.
	 * Not called, if an exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where mouse is moved to
	 * @param xOffset
	 *            offset in x direction
	 * @param yOffset
	 *            offset in y direction
	 */
	void afterMouseMove(Step step, Coordinates where, long xOffset, long yOffset);

	/**
	 * Called before {@link org.openqa.selenium.interactions.Mouse#contextClick Mouse.contextClick(...)}.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where context click is performed
	 */
	void beforeContextClick(Step step, Coordinates where);

	/**
	 * Called after {@link org.openqa.selenium.interactions.Mouse#contextClick Mouse.contextClick(...)}}. Not called, if an
	 * exception is thrown.
	 * @param step
	 *            step record
	 * @param where
	 *            coordinates where context click is performed
	 */
	void afterContextClick(Step step, Coordinates where);

	/**
	 * Called whenever a command throws an exception.
	 * @param step
	 *            step record
	 * @param cmd
	 *            the command which ran into an issue
	 * @param throwable
	 *            the exception that will be thrown
	 */
	void onException(Step step, Cmd cmd, Throwable throwable);
	
	/**
	 * Gets the list of events logged so far by the implementing listener.
	 * 
	 * Preferably it should return an immutable list by wrapping the
	 * list-to-be-returned with {@link Collections#unmodifiableList(List)} so that
	 * multiple calls of this method are allowed.
	 * 
	 * @return list of events logged so far
	 */
	List<Step> getListOfEventsRecorded();
	
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
