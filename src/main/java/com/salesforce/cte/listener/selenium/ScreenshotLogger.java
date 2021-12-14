/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener.selenium;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

import com.salesforce.cte.common.TestEvent;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScreenshotLogger extends AbstractEventListener {
	private ThreadLocal<String> cachedSendKeysLocator = new ThreadLocal<>();
    
    private TakesScreenshot tss;
    
    public ScreenshotLogger(WebDriver driver){
        this.tss = (TakesScreenshot)driver;
    }

	public void setWebDriver(WebDriver driver){
		this.tss = (TakesScreenshot)driver;
	}
	
    /*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object.
	 *--------------------------------------------------------------------*/
    @Override
	public void beforeClose(WebDriverEvent event) {
        captureScreenShot(event); 
	}

	@Override
	public void beforeGet(WebDriverEvent event, String url) {
		captureScreenShot(event);
	}

    /*--------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver object
	 * after casting to JavascriptExecutor.
	 *--------------------------------------------------------------------*/

    @Override
	public void beforeExecuteScript(WebDriverEvent event, String script, Map<String, ?> params) {
        if (script.contains("click")){
            captureScreenShot(event);
        }
	}

    /*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Navigation object.
	 *---------------------------------------------------------------------------*/
    @Override
	public void beforeBack(WebDriverEvent event) {
        captureScreenShot(event);
	}

	@Override
	public void beforeForward(WebDriverEvent event) {
        captureScreenShot(event);
	}

	/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebElement object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeClick(WebDriverEvent event, WebElement element) {
        captureScreenShot(event);   
	}

	@Override
	public void beforeClear(WebDriverEvent event, WebElement element) {
        captureScreenShot(event);   
	}

	@Override
	public void beforeSendKeysByElement(WebDriverEvent event, WebElement element, CharSequence... keysToSend) {
		// Skip capturing a screenshot if it is the same locator, because it means
		// a test is sending text character by character to the same text field.
		if (isDifferentLocator(element))
			captureScreenShot(event);
	}

	@Override
	public void beforeSubmit(WebDriverEvent event, WebElement element) {
        captureScreenShot(event);   
    }

    /*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Alert object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeDismiss(WebDriverEvent event) {
        captureScreenShot(event);
	}

	@Override
	public void beforeAccept(WebDriverEvent event) {
        captureScreenShot(event);
	}

	@Override
	public void beforeSendKeysByAlert(WebDriverEvent event, String keysToSend) {
        captureScreenShot(event);
	}

    private void captureScreenShot(WebDriverEvent event){
        logEntries.add(event);
        File file=tss.getScreenshotAs(OutputType.FILE);
        TestEvent testEvent = createTestEvent(event,Level.SEVERE);
		testEvent.setStreenshotPath(file.getAbsolutePath());
        administrator.getTestCaseExecution().appendEvent(testEvent);
    }
    
    private boolean isDifferentLocator(WebElement elem) {
    	String locator = WebDriverEvent.getLocatorFromWebElement(elem);
    	// if locator is null we assume it's a different locator
    	if (locator == null)
    		return true;
    	
    	String cachedLocator = cachedSendKeysLocator.get();
    	// if cachedLocator is null we assume it's a different locator
    	if (cachedLocator == null) {
    		// cache the currently used locator
    		cachedSendKeysLocator.set(locator);
    		return true;    		
    	}

    	if (cachedLocator.equals(locator))
    		return false;
    	
    	// currently used locator is different from cached one; replace the cached locator
    	cachedSendKeysLocator.set(locator);
    	return true;
    }
}
