/**
 * 
 */
package com.salesforce.cqe.driver;

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

import com.salesforce.cqe.driver.listener.IEventListener;
import com.salesforce.cqe.driver.listener.FullLogger;
import com.salesforce.cqe.driver.listener.Step;
import com.salesforce.cqe.driver.listener.Step.Cmd;
import com.salesforce.cqe.driver.listener.Step.Type;

/**
 * Entry point for all WebDriver classes augmented by us to fit DrillBit's needs.
 * @author gneumann
 * @since 1.0
 */
public class EventDispatcher {
	private static EventDispatcher instance = null;

	private final List<IEventListener> eventListeners = new ArrayList<>();
	private Step currentStep = null;
	private int stepNumber = 0;

	public static EventDispatcher getInstance() {
		if (instance == null)
			instance = new EventDispatcher();
		return instance;
	}
	
	private EventDispatcher() {
		// TODO for the moment hardcode all event listeners here. We can make it dynamic at a later point in time.
		eventListeners.add(new FullLogger());
	}

	public List<IEventListener> getImmutableListOfEventListeners() {
		return Collections.unmodifiableList(eventListeners);
	}
	
	public void beforeGet(String url) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.get);
		step.setParam1(url);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGet(step, url);
	}

	public void afterGet(String url) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.get);
		step.setParam1(url);
		for (IEventListener listener : eventListeners)
			listener.afterGet(step, url);
	}

	public void beforeGetTitle() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getTitle);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTitle(step);
	}

	public void afterGetTitle(String title) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getTitle);
		step.setReturnValue(title);
		for (IEventListener listener : eventListeners)
			listener.afterGetTitle(step, title);
	}

	public void beforeGetCurrentUrl() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getCurrentUrl);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCurrentUrl(step);
	}

	public void afterGetCurrentUrl(String url) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.get);
		step.setReturnValue(url);
		for (IEventListener listener : eventListeners)
			listener.afterGetCurrentUrl(step, url);
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getScreenshotAs);
		step.setParam1(target.toString());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetScreenshotAs(step, target);
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getScreenshotAs);
		step.setParam1(target.toString());
		step.setReturnObject(screenshot);
		for (IEventListener listener : eventListeners)
			listener.afterGetScreenshotAs(step, target, screenshot);
	}

	public void beforeFindElementsByWebDriver(By by) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.findElementsByWebDriver);
		step.setParam1(Step.getLocatorFromBy(by));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementsByWebDriver(step, by);
	}

	public void afterFindElementsByWebDriver(List<WebElement> elements, By by) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.findElementsByWebDriver);
		step.setParam1(Step.getLocatorFromBy(by));
		if (elements.size() > 0) {
			if (elements.size() == 1)
				step.setReturnValue(Step.getLocatorFromWebElement(elements.get(0)));
			else
				step.setReturnValue(Step.getLocatorFromWebElement(elements.get(0)) + " and "
						+ (elements.size() - 1) + " more");				
		}
		step.setReturnObject(elements);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementsByWebDriver(step, elements, by);
	}

	public void beforeFindElementByWebDriver(By by) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.findElementByWebDriver);
		step.setParam1(Step.getLocatorFromBy(by));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementByWebDriver(step, by);
	}

	public void afterFindElementByWebDriver(WebElement element, By by) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.findElementByWebDriver);
		step.setParam1(Step.getLocatorFromBy(by));
		step.setReturnValue(Step.getLocatorFromWebElement(element));
		step.setReturnObject(element);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementByWebDriver(step, element, by);
	}

	public void beforeGetPageSource() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getPageSource);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetPageSource(step);
	}

	public void afterGetPageSource(String source) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getPageSource);
		step.setReturnValue(source);
		for (IEventListener listener : eventListeners)
			listener.afterGetPageSource(step, source);		
	}

	public void beforeClose() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.close);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeClose(step);
	}

	public void afterClose() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.get);
		for (IEventListener listener : eventListeners)
			listener.afterClose(step);
	}

	public void beforeQuit() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.quit);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeQuit(step);
	}

	public void afterQuit() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.quit);
		for (IEventListener listener : eventListeners)
			listener.afterQuit(step);
	}

	public void beforeGetWindowHandles() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getWindowHandles);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetWindowHandles(step);
	}

	public void afterGetWindowHandles(Set<String> handles) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getWindowHandles);
		step.setReturnObject(handles);
		for (IEventListener listener : eventListeners)
			listener.afterGetWindowHandles(step, handles);
	}

	public void beforeGetWindowHandle() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getWindowHandle);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetWindowHandle(step);
	}

	public void afterGetWindowHandle(String handle) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getWindowHandle);
		step.setReturnValue(handle);
		for (IEventListener listener : eventListeners)
			listener.afterGetWindowHandle(step, handle);
	}

	public void beforeExecuteScript(String script, Map<String, ?> params) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.executeScript);
		step.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			step.setParam2(param2.substring(0, param2.length()-1));
		}
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeExecuteScript(step, script, params);
	}

	public void afterExecuteScript(String script, Map<String, ?> params, Object result) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.executeScript);
		step.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			step.setParam2(param2.substring(0, param2.length()-1));
		}
		step.setReturnObject(result);
		for (IEventListener listener : eventListeners)
			listener.afterExecuteScript(step, script, params, result);
	}

	public void beforeExecuteAsyncScript(String script, Map<String, ?> params) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.executeAsyncScript);
		step.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			step.setParam2(param2.substring(0, param2.length()-1));
		}
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeExecuteAsyncScript(step, script, params);
	}

	public void afterExecuteAsyncScript(String script, Map<String, ?> params, Object result) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.executeAsyncScript);
		step.setParam1(script);
		if (!params.isEmpty()) {
			Set<String> arguments = params.keySet();
			StringBuilder b = new StringBuilder();
			for (String arg : arguments)
				b.append(arg).append(",");
			String param2 = b.toString();
			// drop the trailing ','
			step.setParam2(param2.substring(0, param2.length()-1));
		}
		step.setReturnObject(result);
		for (IEventListener listener : eventListeners)
			listener.afterExecuteAsyncScript(step, script, params, result);
	}

	public void beforeAddCookie(Cookie cookie) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.addCookie);
		step.setParam1(cookie.toString());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeAddCookie(step, cookie);
	}

	public void afterAddCookie(Cookie cookie) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.addCookie);
		step.setParam1(cookie.toString());
		for (IEventListener listener : eventListeners)
			listener.afterAddCookie(step, cookie);
	}

	public void beforeDeleteCookieNamed(String name) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.deleteCookieNamed);
		step.setParam1(name);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteCookieNamed(step, name);
	}

	public void afterDeleteCookieNamed(String name) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.deleteCookieNamed);
		step.setParam1(name);
		for (IEventListener listener : eventListeners)
			listener.afterDeleteCookieNamed(step, name);
	}

	public void beforeDeleteCookie(Cookie cookie) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.deleteCookie);
		step.setParam1(cookie.toString());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteCookie(step, cookie);
	}

	public void afterDeleteCookie(Cookie cookie) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.deleteCookie);
		step.setParam1(cookie.toString());
		for (IEventListener listener : eventListeners)
			listener.afterDeleteCookie(step, cookie);
	}

	public void beforeDeleteAllCookies() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.deleteAllCookies);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDeleteAllCookies(step);
	}

	public void afterDeleteAllCookies() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.deleteAllCookies);
		for (IEventListener listener : eventListeners)
			listener.afterDeleteAllCookies(step);
	}

	public void beforeGetCookies() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.getCookies);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCookies(step);
	}

	public void afterGetCookies(Set<Cookie> cookies) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.getCookies);
		step.setReturnObject(cookies);
		for (IEventListener listener : eventListeners)
			listener.afterGetCookies(step, cookies);
	}

	public void beforeGetCookieNamed(String name) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.getCookieNamed);
		step.setParam1(name);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCookieNamed(step, name);
	}

	public void afterGetCookieNamed(String name, Cookie cookie) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.getCookieNamed);
		step.setParam1(name);
		step.setReturnObject(cookie);
		for (IEventListener listener : eventListeners)
			listener.afterGetCookieNamed(step, name, cookie);
	}

	public void beforeGetAvailableEngines() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getAvailableEngines);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetAvailableEngines(step);
	}

	public void afterGetAvailableEngines(List<String> engines) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getAvailableEngines);
		step.setReturnObject(engines);
		for (IEventListener listener : eventListeners)
			listener.afterGetAvailableEngines(step, engines);
	}

	public void beforeGetActiveEngine() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getActiveEngine);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetActiveEngine(step);
	}

	public void afterGetActiveEngine(String engine) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getActiveEngine);
		step.setReturnValue(engine);
		for (IEventListener listener : eventListeners)
			listener.afterGetActiveEngine(step, engine);
	}

	public void beforeIsActivated() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.isActivated);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeIsActivated(step);
	}

	public void afterIsActivated(boolean isActivated) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.isActivated);
		step.setReturnValue(Boolean.toString(isActivated));
		for (IEventListener listener : eventListeners)
			listener.afterIsActivated(step, isActivated);
	}

	public void beforeDeactivate() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.deactivate);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDeactivate(step);
	}

	public void afterDeactivate() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.deactivate);
		for (IEventListener listener : eventListeners)
			listener.afterDeactivate(step);
	}

	public void beforeActivateEngine(String engine) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.activateEngine);
		step.setParam1(engine);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeActivateEngine(step, engine);
	}

	public void afterActivateEngine(String engine) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.activateEngine);
		step.setParam1(engine);
		for (IEventListener listener : eventListeners)
			listener.afterActivateEngine(step, engine);
	}

	public void beforeImplicitlyWait(long time, TimeUnit unit) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.implicitlyWait);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeImplicitlyWait(step, time, unit);
	}

	public void afterImplicitlyWait(long time, TimeUnit unit) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.implicitlyWait);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterImplicitlyWait(step, time, unit);
	}

	public void beforeSetScriptTimeout(long time, TimeUnit unit) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.setScriptTimeout);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSetScriptTimeout(step, time, unit);
	}

	public void afterSetScriptTimeout(long time, TimeUnit unit) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.setScriptTimeout);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterSetScriptTimeout(step, time, unit);
	}

	public void beforePageLoadTimeout(long time, TimeUnit unit) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.pageLoadTimeout);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforePageLoadTimeout(step, time, unit);
	}

	public void afterPageLoadTimeout(long time, TimeUnit unit) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.pageLoadTimeout);
		step.setParam1(Long.toString(time));
		step.setParam2(unit.name());
		for (IEventListener listener : eventListeners)
			listener.afterPageLoadTimeout(step, time, unit);
	}

	public void beforeSetSizeByWindow(Dimension targetSize) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.setSizeByWindow);
		step.setParam1(targetSize.getHeight() + "x" + targetSize.getWidth());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSetSizeByWindow(step, targetSize);
	}

	public void afterSetSizeByWindow(Dimension targetSize) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.setSizeByWindow);
		step.setParam1(targetSize.getHeight() + "x" + targetSize.getWidth());
		for (IEventListener listener : eventListeners)
			listener.afterSetSizeByWindow(step, targetSize);
	}

	public void beforeSetPosition(Point targetPosition) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.setPosition);
		step.setParam1("x:" + targetPosition.x + ",y:" + targetPosition.y);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSetPosition(step, targetPosition);
	}

	public void afterSetPosition(Point targetPosition) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.setPosition);
		step.setParam1("x:" + targetPosition.x + ",y:" + targetPosition.y);
		for (IEventListener listener : eventListeners)
			listener.afterSetPosition(step, targetPosition);
	}

	public void beforeGetSizeByWindow() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getSizeByWindow);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetSizeByWindow(step);
	}

	public void afterGetSizeByWindow(Dimension size) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getSizeByWindow);
		step.setReturnValue(String.format("h:%d,w:%d", size.height, size.width));
		step.setReturnObject(size);
		for (IEventListener listener : eventListeners)
			listener.afterGetSizeByWindow(step, size);
	}

	public void beforeGetPosition() {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getPosition);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetPosition(step);
	}

	public void afterGetPosition(Point targetPosition) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getPosition);
		step.setReturnObject(targetPosition);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.afterGetPosition(step, targetPosition);
	}

	public void beforeMaximize() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.maximize);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeMaximize(step);
	}

	public void afterMaximize() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.maximize);
		for (IEventListener listener : eventListeners)
			listener.afterMaximize(step);
	}

	public void beforeFullscreen() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.fullscreen);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFullscreen(step);
	}

	public void afterFullscreen() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.fullscreen);
		for (IEventListener listener : eventListeners)
			listener.afterFullscreen(step);
	}

	public void beforeBack() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.back);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeBack(step);
	}

	public void afterBack() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.back);
		for (IEventListener listener : eventListeners)
			listener.afterBack(step);
	}

	public void beforeForward() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.forward);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeForward(step);
	}

	public void afterForward() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.forward);
		for (IEventListener listener : eventListeners)
			listener.afterForward(step);
	}

	public void beforeTo(String url) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.to);
		step.setParam1(url);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeTo(step, url);
	}

	public void afterTo(String url) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.to);
		step.setParam1(url);
		for (IEventListener listener : eventListeners)
			listener.afterTo(step, url);
	}

	public void beforeToUrl(URL url) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.toUrl);
		step.setParam1(url.toString());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeToUrl(step, url);
	}

	public void afterToUrl(URL url) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.toUrl);
		step.setParam1(url.toString());
		for (IEventListener listener : eventListeners)
			listener.afterToUrl(step, url);
	}

	public void beforeRefresh() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.refresh);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeRefresh(step);
	}

	public void afterRefresh() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.refresh);
		for (IEventListener listener : eventListeners)
			listener.afterRefresh(step);
	}

	public void beforeFrameByIndex(int frameIndex) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.frameByIndex);
		step.setParam1("" + frameIndex);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByIndex(step, frameIndex);
	}

	public void afterFrameByIndex(int frameIndex) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.frameByIndex);
		step.setParam1("" + frameIndex);
		for (IEventListener listener : eventListeners)
			listener.afterFrameByIndex(step, frameIndex);
	}

	public void beforeFrameByName(String frameName) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.frameByName);
		step.setParam1(frameName);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByName(step, frameName);
	}

	public void afterFrameByName(String frameName) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.frameByName);
		step.setParam1(frameName);
		for (IEventListener listener : eventListeners)
			listener.afterFrameByName(step, frameName);
	}

	public void beforeFrameByElement(WebElement frameElement) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.frameByElement);
		step.setParam1(Step.getLocatorFromWebElement(frameElement));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFrameByElement(step, frameElement);
	}

	public void afterFrameByElement(WebElement frameElement) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.frameByElement);
		step.setParam1(Step.getLocatorFromWebElement(frameElement));
		for (IEventListener listener : eventListeners)
			listener.afterFrameByElement(step, frameElement);
	}

	public void beforeParentFrame() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.parentFrame);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeParentFrame(step);
	}

	public void afterParentFrame() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.parentFrame);
		for (IEventListener listener : eventListeners)
			listener.afterParentFrame(step);
	}

	public void beforeWindow(String windowHandleOrName) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.window);
		step.setParam1(windowHandleOrName);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeWindow(step, windowHandleOrName);
	}

	public void afterWindow(String windowHandleOrName) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.window);
		step.setParam1(windowHandleOrName);
		for (IEventListener listener : eventListeners)
			listener.afterWindow(step, windowHandleOrName);
	}

	public void beforeDefaultContent() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.defaultContent);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDefaultContent(step);
	}

	public void afterDefaultContent() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.defaultContent);
		for (IEventListener listener : eventListeners)
			listener.afterDefaultContent(step);
	}
	
	public void beforeActiveElement() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.activeElement);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeActiveElement(step);
	}

	public void afterActiveElement(WebElement activeElement) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.activeElement);
		step.setReturnValue(Step.getLocatorFromWebElement(activeElement));
		step.setReturnObject(activeElement);
		for (IEventListener listener : eventListeners)
			listener.afterActiveElement(step, activeElement);
	}

	public void beforeAlert() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.alert);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeAlert(step);
	}

	public void afterAlert(Alert alert) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.alert);
		step.setReturnObject(alert);
		for (IEventListener listener : eventListeners)
			listener.afterAlert(step, alert);
	}

	/* End of methods provided by RemoteTargetLocator */

	/* Begin of methods provided by RemoteAlert class */

	public void beforeDismiss() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.dismiss);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDismiss(step);
	}

	public void afterDismiss() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.dismiss);
		for (IEventListener listener : eventListeners)
			listener.afterDismiss(step);
	}

	public void beforeAccept() {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.accept);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeAccept(step);
	}

	public void afterAccept() {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.accept);
		for (IEventListener listener : eventListeners)
			listener.afterAccept(step);
	}

	public void beforeGetTextByAlert() {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getTextByAlert);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTextByAlert(step);
	}

	public void afterGetTextByAlert(String text) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getTextByAlert);
		step.setReturnValue(text);
		for (IEventListener listener : eventListeners)
			listener.afterGetTextByAlert(step, text);
	}

	public void beforeSendKeysByAlert(String keysToSend) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.sendKeysByAlert);
		step.setParam1(keysToSend);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByAlert(step, keysToSend);
	}

	public void afterSendKeysByAlert(String keysToSend) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.accept);
		step.setParam1(keysToSend);
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByAlert(step, keysToSend);
	}

	/* End of methods provided by RemoteWebDriver and its inner classes */

	/* Begin of methods provided by RemoteWebElement class */
	
	public void beforeClick(WebElement element) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.clickByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeClick(step, element);
	}

	public void afterClick(WebElement element) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.clickByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterClick(step, element);
	}

	public void beforeSubmit(WebElement element) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.submit);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSubmit(step, element);
	}

	public void afterSubmit(WebElement element) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.submit);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterSubmit(step, element);
	}

	public void beforeSendKeysByElement(WebElement element, CharSequence... keysToSend) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.sendKeysByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(maskTextIfPassword(step.getElementLocator(), keysToSend));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByElement(step, element, keysToSend);
	}

	public void afterSendKeysByElement(WebElement element, CharSequence... keysToSend) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.sendKeysByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(maskTextIfPassword(step.getElementLocator(), keysToSend));
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByElement(step, element, keysToSend);
	}

	public void beforeUploadFile(WebElement element, File localFile) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.uploadFile);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(localFile.getPath());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeUploadFile(step, element, localFile);
	}

	public void afterUploadFile(WebElement element, File localFile, String response) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.uploadFile);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(localFile.getPath());
		for (IEventListener listener : eventListeners)
			listener.afterUploadFile(step, element, localFile, response);
	}

	public void beforeClear(WebElement element) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.clear);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeClear(step, element);
	}

	public void afterClear(WebElement element) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.clear);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		for (IEventListener listener : eventListeners)
			listener.afterClear(step, element);
	}

	public void beforeGetTagName(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getTagName);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetTagName(step, element);
	}

	public void afterGetTagName(String tagName, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getTagName);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(tagName);
		for (IEventListener listener : eventListeners)
			listener.afterGetTagName(step, tagName, element);		
	}

	public void beforeGetAttribute(String name, WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getAttribute);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(name);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetAttribute(step, name, element);
	}

	public void afterGetAttribute(String value, String name, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getAttribute);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(name);
		step.setReturnValue(value);
		for (IEventListener listener : eventListeners)
			listener.afterGetAttribute(step, value, name, element);		
	}

	public void beforeIsSelected(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.isSelected);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeIsSelected(step, element);
	}

	public void afterIsSelected(boolean isSelected, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.isSelected);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(Boolean.toString(isSelected));
		for (IEventListener listener : eventListeners)
			listener.afterIsSelected(step, isSelected, element);		
	}

	public void beforeIsEnabled(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.isEnabled);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeIsEnabled(step, element);
	}

	public void afterIsEnabled(boolean isEnabled, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.isEnabled);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(Boolean.toString(isEnabled));
		for (IEventListener listener : eventListeners)
			listener.afterIsEnabled(step, isEnabled, element);		
	}

	public void beforeGetText(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getText);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetText(step, element);
	}

	public void afterGetText(String text, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getText);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(text);
		for (IEventListener listener : eventListeners)
			listener.afterGetText(step, text, element);		
	}

	public void beforeGetCssValue(String propertyName, WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getCssValue);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(propertyName);
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCssValue(step, propertyName, element);
	}

	public void afterGetCssValue(String propertyName, String value, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getCssValue);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(propertyName);
		step.setReturnValue(value);
		for (IEventListener listener : eventListeners)
			listener.afterGetCssValue(step, propertyName, value, element);		
	}

	public void beforeFindElementsByElement(By by, WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.findElementsByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(Step.getLocatorFromBy(by));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementsByElement(step, by, element);
	}

	public void afterFindElementsByElement(List<WebElement> elements, By by, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.findElementsByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(Step.getLocatorFromBy(by));
		if (elements.size() > 0) {
			if (elements.size() == 1)
				step.setReturnValue(Step.getLocatorFromWebElement(elements.get(0)));
			else
				step.setReturnValue(Step.getLocatorFromWebElement(elements.get(0)) + " and "
						+ (elements.size() - 1) + " more");				
		}
		step.setReturnObject(elements);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementsByElement(step, elements, by, element);
	}

	public void beforeFindElementByElement(By by, WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.findElementByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(Step.getLocatorFromBy(by));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeFindElementByElement(step, by, element);
	}

	public void afterFindElementByElement(WebElement returnedElement, By by, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.findElementByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(Step.getLocatorFromBy(by));
		step.setReturnValue(Step.getLocatorFromWebElement(element));
		step.setReturnObject(element);
		for (IEventListener listener : eventListeners)
			listener.afterFindElementByElement(step, element, by, element);
	}

	public void beforeIsDisplayed(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.isDisplayed);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeIsDisplayed(step, element);
	}

	public void afterIsDisplayed(boolean isDisplayed, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.isDisplayed);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(Boolean.toString(isDisplayed));
		for (IEventListener listener : eventListeners)
			listener.afterIsDisplayed(step, isDisplayed, element);		
	}

	public void beforeGetLocation(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getLocation);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetLocation(step, element);
	}

	public void afterGetLocation(Point point, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getLocation);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(String.format("x:%d,y:%d", point.x, point.y));
		step.setReturnObject(point);
		for (IEventListener listener : eventListeners)
			listener.afterGetLocation(step, point, element);		
	}

	public void beforeGetSizeByElement(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getSizeByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetSizeByElement(step, element);
	}

	public void afterGetSizeByElement(Dimension size, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getSizeByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(String.format("h:%d,w:%d", size.height, size.width));
		step.setReturnObject(size);
		for (IEventListener listener : eventListeners)
			listener.afterGetSizeByElement(step, size, element);		
	}

	public void beforeGetRect(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getRect);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetRect(step, element);
	}

	public void afterGetRect(Rectangle rectangle, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getRect);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(String.format("h:%d,w:%d", rectangle.height, rectangle.width));
		step.setReturnObject(rectangle);
		for (IEventListener listener : eventListeners)
			listener.afterGetRect(step, rectangle, element);		
	}

	public void beforeGetCoordinates(WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getCoordinates);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetCoordinates(step, element);
	}

	public void afterGetCoordinates(Coordinates coordinates, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getCoordinates);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setReturnValue(String.format("x:%d,y:%d in view port", coordinates.inViewPort().x, coordinates.inViewPort().y));
		step.setReturnObject(coordinates);
		for (IEventListener listener : eventListeners)
			listener.afterGetCoordinates(step, coordinates, element);		
	}

	public <X> void beforeGetScreenshotAsByElement(OutputType<X> target, WebElement element) {
		Step step = new Step(Type.BeforeGather, stepNumber, Cmd.getScreenshotAsByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(target.toString());
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeGetScreenshotAsByElement(step, target, element);
	}

	public <X> void afterGetScreenshotAsByElement(OutputType<X> target, X screenshot, WebElement element) {
		Step step = new Step(Type.AfterGather, stepNumber, Cmd.getScreenshotAsByElement);
		step.setElementLocator(Step.getLocatorFromWebElement(element));
		step.setParam1(target.toString());
		step.setReturnObject(screenshot);
		for (IEventListener listener : eventListeners)
			listener.afterGetScreenshotAsByElement(step, target, screenshot, element);
	}
	
	/* End of methods provided by RemoteWebElement class */

	/* Begin of methods provided by RemoteKeyboard class */

	public void beforeSendKeysByKeyboard(CharSequence... keysToSend) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.sendKeysByKeyboard);
		step.setParam1(charSequence2String(keysToSend));
		for (IEventListener listener : eventListeners)
			listener.beforeSendKeysByKeyboard(step, keysToSend);
	}

	public void afterSendKeysByKeyboard(CharSequence... keysToSend) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.sendKeysByKeyboard);
		step.setParam1(charSequence2String(keysToSend));
		for (IEventListener listener : eventListeners)
			listener.afterSendKeysByKeyboard(step, keysToSend);
	}

	public void beforePressKey(CharSequence... keyToPress) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.pressKey);
		step.setParam1(charSequence2String(keyToPress));
		for (IEventListener listener : eventListeners)
			listener.beforePressKey(step, keyToPress);
	}

	public void afterPressKey(CharSequence... keyToPress) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.pressKey);
		step.setParam1(charSequence2String(keyToPress));
		for (IEventListener listener : eventListeners)
			listener.afterPressKey(step, keyToPress);
	}

	public void beforeReleaseKey(CharSequence... keyToRelease) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.releaseKey);
		step.setParam1(charSequence2String(keyToRelease));
		for (IEventListener listener : eventListeners)
			listener.beforeReleaseKey(step, keyToRelease);
	}

	public void afterReleaseKey(CharSequence... keyToRelease) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.releaseKey);
		step.setParam1(charSequence2String(keyToRelease));
		for (IEventListener listener : eventListeners)
			listener.afterReleaseKey(step, keyToRelease);
	}
	
	/* End of methods provided by RemoteKeyboard class */

	/* Begin of methods provided by RemoteMouse class */

	public void beforeClickByMouse(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.clickByMouse);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeClickByMouse(step, where);
	}

	public void afterClickByMouse(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.clickByMouse);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterClickByMouse(step, where);
	}

	public void beforeContextClick(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.contextClick);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeContextClick(step, where);
	}

	public void afterContextClick(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.contextClick);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterContextClick(step, where);
	}

	public void beforeDoubleClick(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.doubleClick);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeDoubleClick(step, where);
	}

	public void afterDoubleClick(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.doubleClick);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterDoubleClick(step, where);
	}

	public void beforeMouseDown(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.mouseDown);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseDown(step, where);
	}

	public void afterMouseDown(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.mouseDown);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterMouseDown(step, where);
	}

	public void beforeMouseUp(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.mouseUp);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseUp(step, where);
	}

	public void afterMouseUp(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.mouseUp);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterMouseUp(step, where);
	}

	public void beforeMouseMove(Coordinates where) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.mouseMove);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseMove(step, where);
	}

	public void afterMouseMove(Coordinates where) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.mouseMove);
		step.setParam1(String.format("x:%d,y:%d in view port", where.inViewPort().x, where.inViewPort().y));
		for (IEventListener listener : eventListeners)
			listener.afterMouseMove(step, where);
	}

	public void beforeMouseMove(Coordinates where, long xOffset, long yOffset) {
		Step step = new Step(Type.BeforeAction, stepNumber, Cmd.mouseMoveWithOffset);
		step.setParam1(String.format("x:%d,y:%d in view port, x:%d,y:%d offset", where.inViewPort().x, where.inViewPort().y, xOffset, yOffset));
		currentStep = step;
		for (IEventListener listener : eventListeners)
			listener.beforeMouseMove(step, where, xOffset, yOffset);
	}

	public void afterMouseMove(Coordinates where, long xOffset, long yOffset) {
		Step step = new Step(Type.AfterAction, stepNumber++, Cmd.mouseMoveWithOffset);
		step.setParam1(String.format("x:%d,y:%d in view port, x:%d,y:%d offset", where.inViewPort().x, where.inViewPort().y, xOffset, yOffset));
		for (IEventListener listener : eventListeners)
			listener.afterMouseMove(step, where, xOffset, yOffset);
	}

	public void onException(String cmd, Throwable throwable) {
		Step step = new Step(Type.Exception, stepNumber, currentStep.getCmd());
		step.setParam1(String.format("Exception Type: %s, message: %s", throwable.getClass().getName(), throwable.getMessage()));
		for (IEventListener listener : eventListeners)
			listener.onException(step, currentStep.getCmd(), throwable);
	}

	private String maskTextIfPassword(String locator, CharSequence... charSequence) {
		return (locator.contains("password")) ? "********" : charSequence2String(charSequence);
	}

	private String charSequence2String(CharSequence... charSequence) {
		final StringBuilder sb = new StringBuilder(charSequence.length);
		sb.append(charSequence);
		return sb.toString();
	}
}
