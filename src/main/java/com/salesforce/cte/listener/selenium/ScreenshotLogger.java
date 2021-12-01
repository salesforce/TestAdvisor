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

public class ScreenshotLogger extends AbstractEventListener{
    
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
		String param1 = event.getParam1() == null ? "" : event.getParam1();
		String param2 = event.getParam2() == null ? "" : event.getParam2();
		String cmd = event.getCmd().getLongCmdString();
		cmd = cmd == null ? "" : cmd;
		String locator = event.getElementLocator();
		locator = locator == null ? "" : locator;
        TestEvent testEvent = new TestEvent(event.toString(), 
                                            Level.INFO.toString(),
                                            cmd, 
                                            param1 + param2, 
                                            locator,
                                            event.getRecordNumber(), 
                                            file);
        taAdministrator.getTestCaseExecution().appendEvent(testEvent);
    }
}
