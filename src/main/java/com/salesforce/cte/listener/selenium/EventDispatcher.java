/**
 * 
 */
package com.salesforce.cte.listener.selenium;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.salesforce.cte.listener.selenium.WebDriverEvent.Cmd;
import com.salesforce.cte.listener.selenium.WebDriverEvent.Type;

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

/**
 * Entry point for all WebDriver classes augmented by us to fit Test Advisor's needs.
 * @author gneumann
 * @since 1.0
 */
public class EventDispatcher {
	private static EventDispatcher instance = null;

	private final List<IEventListener> eventListeners = new ArrayList<>();
	private WebDriverEvent currentEvent = null;
	private int eventNumber = 0;
	
	public static EventDispatcher getInstance(WebDriver driver) {
		if (instance == null)
			instance = new EventDispatcher(driver);
		if (driver != null)
			instance.setWebDriver(driver);
		return instance;
	}

	private EventDispatcher(WebDriver driver) {
		eventListeners.add(new FullLogger());
		eventListeners.add(new ScreenshotLogger(driver));
	}

	public void setWebDriver(WebDriver driver){
		for(IEventListener listener : eventListeners){
			if (listener instanceof ScreenshotLogger){
				((ScreenshotLogger)listener).setWebDriver(driver);
			}
		}
	}

	public List<IEventListener> getImmutableListOfEventListeners() {
		return Collections.unmodifiableList(eventListeners);
	}
	
	public void beforeGet(String url) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.get);
		event.setParam1(url);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGet(event, url);
	}

	public void afterGet(String url) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.get);
		event.setParam1(url);
		for (IEventListener listener : eventListeners)
			listener.afterGet(event, url);
	}

	public void beforeGetTitle() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getTitle);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTitle(event);
	}

	public void afterGetTitle(String title) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getTitle);
		event.setReturnValue(title);
		for (IEventListener listener : eventListeners)
			listener.afterGetTitle(event, title);
	}

	public void beforeGetCurrentUrl() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getCurrentUrl);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCurrentUrl(event);
	}

	public void afterGetCurrentUrl(String url) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.get);
		event.setReturnValue(url);
		for (IEventListener listener : eventListeners)
			listener.afterGetCurrentUrl(event, url);
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getScreenshotAs);
		event.setParam1(target.toString());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetScreenshotAs(event, target);
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getScreenshotAs);
		event.setParam1(target.toString());
		event.setReturnObject(screenshot);
		for (IEventListener listener : eventListeners)
			listener.afterGetScreenshotAs(event, target, screenshot);
	}

	public void beforeFindElementsByWebDriver(By by) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.findElementsByWebDriver);
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementsByWebDriver(event, by);
	}

	public void afterFindElementsByWebDriver(List<WebElement> elements, By by) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.findElementsByWebDriver);
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		if (elements.size() > 0) {
			if (elements.size() == 1)
				event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(elements.get(0)));
			else
				event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(elements.get(0)) + " and "
						+ (elements.size() - 1) + " more");				
		}
		event.setReturnObject(elements);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementsByWebDriver(event, elements, by);
	}

	public void beforeFindElementByWebDriver(By by) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.findElementByWebDriver);
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementByWebDriver(event, by);
	}

	public void afterFindElementByWebDriver(WebElement element, By by) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.findElementByWebDriver);
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnObject(element);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementByWebDriver(event, element, by);
	}

	public void beforeGetPageSource() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getPageSource);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetPageSource(event);
	}

	public void afterGetPageSource(String source) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getPageSource);
		event.setReturnValue(source);
		for (IEventListener listener : eventListeners)
			listener.afterGetPageSource(event, source);		
	}

	public void beforeClose() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.close);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeClose(event);
	}

	public void afterClose() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.get);
		for (IEventListener listener : eventListeners)
			listener.afterClose(event);
	}

	public void beforeQuit() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.quit);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeQuit(event);
	}

	public void afterQuit() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.quit);
		for (IEventListener listener : eventListeners)
			listener.afterQuit(event);
	}

	public void beforeGetWindowHandles() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getWindowHandles);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetWindowHandles(event);
	}

	public void afterGetWindowHandles(Set<String> handles) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getWindowHandles);
		event.setReturnObject(handles);
		for (IEventListener listener : eventListeners)
			listener.afterGetWindowHandles(event, handles);
	}

	public void beforeGetWindowHandle() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getWindowHandle);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetWindowHandle(event);
	}

	public void afterGetWindowHandle(String handle) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getWindowHandle);
		event.setReturnValue(handle);
		for (IEventListener listener : eventListeners)
			listener.afterGetWindowHandle(event, handle);
	}

	public void beforeExecuteScript(String script, Map<String, ?> params) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.executeScript);
		event.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			event.setParam2(param2.substring(0, param2.length()-1));
		}
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeExecuteScript(event, script, params);
	}

	public void afterExecuteScript(String script, Map<String, ?> params, Object result) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.executeScript);
		event.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			event.setParam2(param2.substring(0, param2.length()-1));
		}
		event.setReturnObject(result);
		for (IEventListener listener : eventListeners)
			listener.afterExecuteScript(event, script, params, result);
	}

	public void beforeExecuteAsyncScript(String script, Map<String, ?> params) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.executeAsyncScript);
		event.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			event.setParam2(param2.substring(0, param2.length()-1));
		}
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeExecuteAsyncScript(event, script, params);
	}

	public void afterExecuteAsyncScript(String script, Map<String, ?> params, Object result) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.executeAsyncScript);
		event.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			event.setParam2(param2.substring(0, param2.length()-1));
		}
		event.setReturnObject(result);
		for (IEventListener listener : eventListeners)
			listener.afterExecuteAsyncScript(event, script, params, result);
	}

	public void beforeAddCookie(Cookie cookie) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.addCookie);
		event.setParam1(cookie.toString());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeAddCookie(event, cookie);
	}

	public void afterAddCookie(Cookie cookie) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.addCookie);
		event.setParam1(cookie.toString());
		for (IEventListener listener : eventListeners)
			listener.afterAddCookie(event, cookie);
	}

	public void beforeDeleteCookieNamed(String name) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.deleteCookieNamed);
		event.setParam1(name);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteCookieNamed(event, name);
	}

	public void afterDeleteCookieNamed(String name) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.deleteCookieNamed);
		event.setParam1(name);
		for (IEventListener listener : eventListeners)
			listener.afterDeleteCookieNamed(event, name);
	}

	public void beforeDeleteCookie(Cookie cookie) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.deleteCookie);
		event.setParam1(cookie.toString());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteCookie(event, cookie);
	}

	public void afterDeleteCookie(Cookie cookie) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.deleteCookie);
		event.setParam1(cookie.toString());
		for (IEventListener listener : eventListeners)
			listener.afterDeleteCookie(event, cookie);
	}

	public void beforeDeleteAllCookies() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.deleteAllCookies);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteAllCookies(event);
	}

	public void afterDeleteAllCookies() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.deleteAllCookies);
		for (IEventListener listener : eventListeners)
			listener.afterDeleteAllCookies(event);
	}

	public void beforeGetCookies() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.getCookies);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCookies(event);
	}

	public void afterGetCookies(Set<Cookie> cookies) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.getCookies);
		event.setReturnObject(cookies);
		for (IEventListener listener : eventListeners)
			listener.afterGetCookies(event, cookies);
	}

	public void beforeGetCookieNamed(String name) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.getCookieNamed);
		event.setParam1(name);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCookieNamed(event, name);
	}

	public void afterGetCookieNamed(String name, Cookie cookie) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.getCookieNamed);
		event.setParam1(name);
		event.setReturnObject(cookie);
		for (IEventListener listener : eventListeners)
			listener.afterGetCookieNamed(event, name, cookie);
	}

	public void beforeGetAvailableEngines() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getAvailableEngines);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetAvailableEngines(event);
	}

	public void afterGetAvailableEngines(List<String> engines) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getAvailableEngines);
		event.setReturnObject(engines);
		for (IEventListener listener : eventListeners)
			listener.afterGetAvailableEngines(event, engines);
	}

	public void beforeGetActiveEngine() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getActiveEngine);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetActiveEngine(event);
	}

	public void afterGetActiveEngine(String engine) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getActiveEngine);
		event.setReturnValue(engine);
		for (IEventListener listener : eventListeners)
			listener.afterGetActiveEngine(event, engine);
	}

	public void beforeIsActivated() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.isActivated);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeIsActivated(event);
	}

	public void afterIsActivated(boolean isActivated) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.isActivated);
		event.setReturnValue(Boolean.toString(isActivated));
		for (IEventListener listener : eventListeners)
			listener.afterIsActivated(event, isActivated);
	}

	public void beforeDeactivate() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.deactivate);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDeactivate(event);
	}

	public void afterDeactivate() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.deactivate);
		for (IEventListener listener : eventListeners)
			listener.afterDeactivate(event);
	}

	public void beforeActivateEngine(String engine) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.activateEngine);
		event.setParam1(engine);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeActivateEngine(event, engine);
	}

	public void afterActivateEngine(String engine) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.activateEngine);
		event.setParam1(engine);
		for (IEventListener listener : eventListeners)
			listener.afterActivateEngine(event, engine);
	}

	public void beforeImplicitlyWait(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.implicitlyWait);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeImplicitlyWait(event, time, unit);
	}

	public void afterImplicitlyWait(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.implicitlyWait);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterImplicitlyWait(event, time, unit);
	}

	public void beforeSetScriptTimeout(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.setScriptTimeout);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSetScriptTimeout(event, time, unit);
	}

	public void afterSetScriptTimeout(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.setScriptTimeout);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterSetScriptTimeout(event, time, unit);
	}

	public void beforePageLoadTimeout(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.pageLoadTimeout);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforePageLoadTimeout(event, time, unit);
	}

	public void afterPageLoadTimeout(long time, TimeUnit unit) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.pageLoadTimeout);
		event.setParam1(Long.toString(time));
		event.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterPageLoadTimeout(event, time, unit);
	}

	public void beforeSetSizeByWindow(Dimension targetSize) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.setSizeByWindow);
		event.setParam1(targetSize.getHeight() + "x" + targetSize.getWidth());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSetSizeByWindow(event, targetSize);
	}

	public void afterSetSizeByWindow(Dimension targetSize) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.setSizeByWindow);
		event.setParam1(targetSize.getHeight() + "x" + targetSize.getWidth());
		for (IEventListener listener : eventListeners)
			listener.afterSetSizeByWindow(event, targetSize);
	}

	public void beforeSetPosition(Point targetPosition) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.setPosition);
		event.setParam1("x:" + targetPosition.x + ",y:" + targetPosition.y);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSetPosition(event, targetPosition);
	}

	public void afterSetPosition(Point targetPosition) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.setPosition);
		event.setParam1("x:" + targetPosition.x + ",y:" + targetPosition.y);
		for (IEventListener listener : eventListeners)
			listener.afterSetPosition(event, targetPosition);
	}

	public void beforeGetSizeByWindow() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getSizeByWindow);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetSizeByWindow(event);
	}

	public void afterGetSizeByWindow(Dimension size) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getSizeByWindow);
		event.setReturnValue(String.format("h:%d,w:%d", size.height, size.width));
		event.setReturnObject(size);
		for (IEventListener listener : eventListeners)
			listener.afterGetSizeByWindow(event, size);
	}

	public void beforeGetPosition() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getPosition);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetPosition(event);
	}

	public void afterGetPosition(Point targetPosition) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getPosition);
		event.setReturnObject(targetPosition);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.afterGetPosition(event, targetPosition);
	}

	public void beforeMaximize() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.maximize);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeMaximize(event);
	}

	public void afterMaximize() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.maximize);
		for (IEventListener listener : eventListeners)
			listener.afterMaximize(event);
	}

	public void beforeFullscreen() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.fullscreen);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFullscreen(event);
	}

	public void afterFullscreen() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.fullscreen);
		for (IEventListener listener : eventListeners)
			listener.afterFullscreen(event);
	}

	public void beforeBack() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.back);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeBack(event);
	}

	public void afterBack() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.back);
		for (IEventListener listener : eventListeners)
			listener.afterBack(event);
	}

	public void beforeForward() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.forward);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeForward(event);
	}

	public void afterForward() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.forward);
		for (IEventListener listener : eventListeners)
			listener.afterForward(event);
	}

	public void beforeTo(String url) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.to);
		event.setParam1(url);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeTo(event, url);
	}

	public void afterTo(String url) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.to);
		event.setParam1(url);
		for (IEventListener listener : eventListeners)
			listener.afterTo(event, url);
	}

	public void beforeToUrl(URL url) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.toUrl);
		event.setParam1(url.toString());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeToUrl(event, url);
	}

	public void afterToUrl(URL url) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.toUrl);
		event.setParam1(url.toString());
		for (IEventListener listener : eventListeners)
			listener.afterToUrl(event, url);
	}

	public void beforeRefresh() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.refresh);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeRefresh(event);
	}

	public void afterRefresh() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.refresh);
		for (IEventListener listener : eventListeners)
			listener.afterRefresh(event);
	}

	public void beforeFrameByIndex(int frameIndex) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.frameByIndex);
		event.setParam1("" + frameIndex);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByIndex(event, frameIndex);
	}

	public void afterFrameByIndex(int frameIndex) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.frameByIndex);
		event.setParam1("" + frameIndex);
		for (IEventListener listener : eventListeners)
			listener.afterFrameByIndex(event, frameIndex);
	}

	public void beforeFrameByName(String frameName) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.frameByName);
		event.setParam1(frameName);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByName(event, frameName);
	}

	public void afterFrameByName(String frameName) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.frameByName);
		event.setParam1(frameName);
		for (IEventListener listener : eventListeners)
			listener.afterFrameByName(event, frameName);
	}

	public void beforeFrameByElement(WebElement frameElement) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.frameByElement);
		event.setParam1(WebDriverEvent.getLocatorFromWebElement(frameElement));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByElement(event, frameElement);
	}

	public void afterFrameByElement(WebElement frameElement) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.frameByElement);
		event.setParam1(WebDriverEvent.getLocatorFromWebElement(frameElement));
		for (IEventListener listener : eventListeners)
			listener.afterFrameByElement(event, frameElement);
	}

	public void beforeParentFrame() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.parentFrame);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeParentFrame(event);
	}

	public void afterParentFrame() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.parentFrame);
		for (IEventListener listener : eventListeners)
			listener.afterParentFrame(event);
	}

	public void beforeWindow(String windowHandleOrName) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.window);
		event.setParam1(windowHandleOrName);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeWindow(event, windowHandleOrName);
	}

	public void afterWindow(String windowHandleOrName) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.window);
		event.setParam1(windowHandleOrName);
		for (IEventListener listener : eventListeners)
			listener.afterWindow(event, windowHandleOrName);
	}

	public void beforeDefaultContent() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.defaultContent);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDefaultContent(event);
	}

	public void afterDefaultContent() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.defaultContent);
		for (IEventListener listener : eventListeners)
			listener.afterDefaultContent(event);
	}
	
	public void beforeActiveElement() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.activeElement);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeActiveElement(event);
	}

	public void afterActiveElement(WebElement activeElement) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.activeElement);
		event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(activeElement));
		event.setReturnObject(activeElement);
		for (IEventListener listener : eventListeners)
			listener.afterActiveElement(event, activeElement);
	}

	public void beforeAlert() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.alert);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeAlert(event);
	}

	public void afterAlert(Alert alert) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.alert);
		event.setReturnObject(alert);
		for (IEventListener listener : eventListeners)
			listener.afterAlert(event, alert);
	}

	/* End of methods provided by RemoteTargetLocator */

	/* Begin of methods provided by RemoteAlert class */

	public void beforeDismiss() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.dismiss);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDismiss(event);
	}

	public void afterDismiss() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.dismiss);
		for (IEventListener listener : eventListeners)
			listener.afterDismiss(event);
	}

	public void beforeAccept() {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.accept);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeAccept(event);
	}

	public void afterAccept() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.accept);
		for (IEventListener listener : eventListeners)
			listener.afterAccept(event);
	}

	public void beforeGetTextByAlert() {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getTextByAlert);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTextByAlert(event);
	}

	public void afterGetTextByAlert(String text) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getTextByAlert);
		event.setReturnValue(text);
		for (IEventListener listener : eventListeners)
			listener.afterGetTextByAlert(event, text);
	}

	public void beforeSendKeysByAlert(String keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.sendKeysByAlert);
		event.setParam1(keysToSend);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByAlert(event, keysToSend);
	}

	public void afterSendKeysByAlert(String keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.accept);
		event.setParam1(keysToSend);
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByAlert(event, keysToSend);
	}

	/* End of methods provided by RemoteWebDriver and its inner classes */

	/* Begin of methods provided by RemoteWebElement class */
	
	public void beforeClick(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.clickByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeClick(event, element);
	}

	public void afterClick(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.clickByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterClick(event, element);
	}

	public void beforeSubmit(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.submit);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSubmit(event, element);
	}

	public void afterSubmit(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.submit);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterSubmit(event, element);
	}

	public void beforeSendKeysByElement(WebElement element, CharSequence... keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.sendKeysByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(maskTextIfPassword(event.getElementLocator(), keysToSend));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByElement(event, element, keysToSend);
	}

	public void afterSendKeysByElement(WebElement element, CharSequence... keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.sendKeysByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(maskTextIfPassword(event.getElementLocator(), keysToSend));
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByElement(event, element, keysToSend);
	}

	public void beforeUploadFile(WebElement element, File localFile) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.uploadFile);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(localFile.getPath());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeUploadFile(event, element, localFile);
	}

	public void afterUploadFile(WebElement element, File localFile, String response) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.uploadFile);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(localFile.getPath());
		for (IEventListener listener : eventListeners)
			listener.afterUploadFile(event, element, localFile, response);
	}

	public void beforeClear(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.clear);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeClear(event, element);
	}

	public void afterClear(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.clear);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterClear(event, element);
	}

	public void beforeGetTagName(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getTagName);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTagName(event, element);
	}

	public void afterGetTagName(String tagName, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getTagName);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(tagName);
		for (IEventListener listener : eventListeners)
			listener.afterGetTagName(event, tagName, element);		
	}

	public void beforeGetAttribute(String name, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getAttribute);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(name);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetAttribute(event, name, element);
	}

	public void afterGetAttribute(String value, String name, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getAttribute);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(name);
		event.setReturnValue(value);
		for (IEventListener listener : eventListeners)
			listener.afterGetAttribute(event, value, name, element);		
	}

	public void beforeIsSelected(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.isSelected);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeIsSelected(event, element);
	}

	public void afterIsSelected(boolean isSelected, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.isSelected);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(Boolean.toString(isSelected));
		for (IEventListener listener : eventListeners)
			listener.afterIsSelected(event, isSelected, element);		
	}

	public void beforeIsEnabled(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.isEnabled);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeIsEnabled(event, element);
	}

	public void afterIsEnabled(boolean isEnabled, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.isEnabled);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(Boolean.toString(isEnabled));
		for (IEventListener listener : eventListeners)
			listener.afterIsEnabled(event, isEnabled, element);		
	}

	public void beforeGetText(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getText);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetText(event, element);
	}

	public void afterGetText(String text, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getText);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(text);
		for (IEventListener listener : eventListeners)
			listener.afterGetText(event, text, element);		
	}

	public void beforeGetCssValue(String propertyName, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getCssValue);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(propertyName);
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCssValue(event, propertyName, element);
	}

	public void afterGetCssValue(String propertyName, String value, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getCssValue);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(propertyName);
		event.setReturnValue(value);
		for (IEventListener listener : eventListeners)
			listener.afterGetCssValue(event, propertyName, value, element);		
	}

	public void beforeFindElementsByElement(By by, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.findElementsByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementsByElement(event, by, element);
	}

	public void afterFindElementsByElement(List<WebElement> elements, By by, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.findElementsByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		if (elements.size() > 0) {
			if (elements.size() == 1)
				event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(elements.get(0)));
			else
				event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(elements.get(0)) + " and "
						+ (elements.size() - 1) + " more");				
		}
		event.setReturnObject(elements);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementsByElement(event, elements, by, element);
	}

	public void beforeFindElementByElement(By by, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.findElementByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementByElement(event, by, element);
	}

	public void afterFindElementByElement(WebElement returnedElement, By by, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.findElementByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(WebDriverEvent.getLocatorFromBy(by));
		event.setReturnValue(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnObject(element);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementByElement(event, element, by, element);
	}

	public void beforeIsDisplayed(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.isDisplayed);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeIsDisplayed(event, element);
	}

	public void afterIsDisplayed(boolean isDisplayed, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.isDisplayed);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(Boolean.toString(isDisplayed));
		for (IEventListener listener : eventListeners)
			listener.afterIsDisplayed(event, isDisplayed, element);		
	}

	public void beforeGetLocation(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getLocation);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetLocation(event, element);
	}

	public void afterGetLocation(Point point, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getLocation);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(String.format("x:%d,y:%d", point.x, point.y));
		event.setReturnObject(point);
		for (IEventListener listener : eventListeners)
			listener.afterGetLocation(event, point, element);		
	}

	public void beforeGetSizeByElement(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getSizeByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetSizeByElement(event, element);
	}

	public void afterGetSizeByElement(Dimension size, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getSizeByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(String.format("h:%d,w:%d", size.height, size.width));
		event.setReturnObject(size);
		for (IEventListener listener : eventListeners)
			listener.afterGetSizeByElement(event, size, element);		
	}

	public void beforeGetRect(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getRect);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetRect(event, element);
	}

	public void afterGetRect(Rectangle rectangle, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getRect);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(String.format("h:%d,w:%d", rectangle.height, rectangle.width));
		event.setReturnObject(rectangle);
		for (IEventListener listener : eventListeners)
			listener.afterGetRect(event, rectangle, element);		
	}

	public void beforeGetCoordinates(WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getCoordinates);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCoordinates(event, element);
	}

	public void afterGetCoordinates(Coordinates coordinates, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getCoordinates);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setReturnValue(getCoordinatesAsString(coordinates));
		event.setReturnObject(coordinates);
		for (IEventListener listener : eventListeners)
			listener.afterGetCoordinates(event, coordinates, element);		
	}

	public <X> void beforeGetScreenshotAsByElement(OutputType<X> target, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeGather, eventNumber, Cmd.getScreenshotAsByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(target.toString());
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeGetScreenshotAsByElement(event, target, element);
	}

	public <X> void afterGetScreenshotAsByElement(OutputType<X> target, X screenshot, WebElement element) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterGather, eventNumber, Cmd.getScreenshotAsByElement);
		event.setElementLocator(WebDriverEvent.getLocatorFromWebElement(element));
		event.setParam1(target.toString());
		event.setReturnObject(screenshot);
		for (IEventListener listener : eventListeners)
			listener.afterGetScreenshotAsByElement(event, target, screenshot, element);
	}
	
	/* End of methods provided by RemoteWebElement class */

	/* Begin of methods provided by RemoteKeyboard class */

	public void beforeSendKeysByKeyboard(CharSequence... keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.sendKeysByKeyboard);
		event.setParam1(charSequence2String(keysToSend));
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByKeyboard(event, keysToSend);
	}

	public void afterSendKeysByKeyboard(CharSequence... keysToSend) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.sendKeysByKeyboard);
		event.setParam1(charSequence2String(keysToSend));
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByKeyboard(event, keysToSend);
	}

	public void beforePressKey(CharSequence... keyToPress) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.pressKey);
		event.setParam1(charSequence2String(keyToPress));
		for (IEventListener listener : eventListeners)
			listener.beforePressKey(event, keyToPress);
	}

	public void afterPressKey(CharSequence... keyToPress) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.pressKey);
		event.setParam1(charSequence2String(keyToPress));
		for (IEventListener listener : eventListeners)
			listener.afterPressKey(event, keyToPress);
	}

	public void beforeReleaseKey(CharSequence... keyToRelease) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.releaseKey);
		event.setParam1(charSequence2String(keyToRelease));
		for (IEventListener listener : eventListeners)
			listener.beforeReleaseKey(event, keyToRelease);
	}

	public void afterReleaseKey(CharSequence... keyToRelease) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.releaseKey);
		event.setParam1(charSequence2String(keyToRelease));
		for (IEventListener listener : eventListeners)
			listener.afterReleaseKey(event, keyToRelease);
	}
	
	/* End of methods provided by RemoteKeyboard class */

	/* Begin of methods provided by RemoteMouse class */

	public void beforeClickByMouse(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.clickByMouse);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeClickByMouse(event, where);
	}

	public void afterClickByMouse(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.clickByMouse);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterClickByMouse(event, where);
	}

	public void beforeContextClick(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.contextClick);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeContextClick(event, where);
	}

	public void afterContextClick(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.contextClick);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterContextClick(event, where);
	}

	public void beforeDoubleClick(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.doubleClick);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeDoubleClick(event, where);
	}

	public void afterDoubleClick(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.doubleClick);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterDoubleClick(event, where);
	}

	public void beforeMouseDown(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.mouseDown);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseDown(event, where);
	}

	public void afterMouseDown(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.mouseDown);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterMouseDown(event, where);
	}

	public void beforeMouseUp(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.mouseUp);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseUp(event, where);
	}

	public void afterMouseUp(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.mouseUp);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterMouseUp(event, where);
	}

	public void beforeMouseMove(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.mouseMove);
		event.setParam1(getCoordinatesAsString(where));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseMove(event, where);
	}

	public void afterMouseMove(Coordinates where) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.mouseMove);
		event.setParam1(getCoordinatesAsString(where));
		for (IEventListener listener : eventListeners)
			listener.afterMouseMove(event, where);
	}

	public void beforeMouseMove(Coordinates where, long xOffset, long yOffset) {
		WebDriverEvent event = new WebDriverEvent(Type.BeforeAction, eventNumber, Cmd.mouseMoveWithOffset);
		event.setParam1(getCoordinatesWithOffsetAsString(where, xOffset, yOffset));
		currentEvent = event;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseMove(event, where, xOffset, yOffset);
	}

	public void afterMouseMove(Coordinates where, long xOffset, long yOffset) {
		WebDriverEvent event = new WebDriverEvent(Type.AfterAction, eventNumber++, Cmd.mouseMoveWithOffset);
		event.setParam1(getCoordinatesWithOffsetAsString(where, xOffset, yOffset));
		for (IEventListener listener : eventListeners)
			listener.afterMouseMove(event, where, xOffset, yOffset);
	}

	public void onException(String cmd, Throwable throwable) {
		// if the field currentEvent is NULL, then we haven't established
		// a session and it makes no sense to process the exception
		if (currentEvent == null)
			return;

		WebDriverEvent event = new WebDriverEvent(Type.Exception, eventNumber, currentEvent.getCmd());
		event.setParam1(String.format("Exception Type: %s, message: %s", throwable.getClass().getName(), throwable.getMessage()));
		for (IEventListener listener : eventListeners)
			listener.onException(event, currentEvent.getCmd(), throwable);
	}

	private String maskTextIfPassword(String locator, CharSequence... charSequence) {
		return (locator.contains("password")) ? "********" : charSequence2String(charSequence);
	}

	private String charSequence2String(CharSequence... charSequence) {
		final StringBuilder sb = new StringBuilder(charSequence.length);
		sb.append(charSequence);
		return sb.toString();
	}
	
	private String getCoordinatesAsString(Coordinates where) {
		if (where == null || where.inViewPort() == null)
			return "x:<unknown>,y:<unknown> in view port";
		else
			return String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y);
	}

	private String getCoordinatesWithOffsetAsString(Coordinates where, long xOffset, long yOffset) {
		if (where == null || where.inViewPort() == null)
			return String.format("x:<unknown>,y:<unknown> in view port, x:%d,y:%d offset", xOffset, yOffset);
		else
			return String.format("x:%d,y:%d in view port, x:%d,y:%d offset", where.inViewPort().x, where.inViewPort().y, xOffset, yOffset);
	}
}
