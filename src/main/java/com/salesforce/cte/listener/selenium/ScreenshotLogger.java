package com.salesforce.cte.listener.selenium;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;

import com.salesforce.cte.common.TestEvent;
import com.salesforce.cte.listener.selenium.WebDriverEvent.Cmd;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScreenshotLogger extends AbstractEventListener{
    
    private TakesScreenshot tss;
    
    public ScreenshotLogger(WebDriver driver){
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
	public void beforeQuit(WebDriverEvent event) {
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

	@Override
	public void beforeTo(WebDriverEvent event, String url) {
        captureScreenShot(event);
	}

    @Override
	public void beforeToUrl(WebDriverEvent event, URL url) {
        captureScreenShot(event);
	}

/*---------------------------------------------------------------------------
	 * Section for all commands called directly from WebDriver.Window object.
	 *---------------------------------------------------------------------------*/

	@Override
	public void beforeFullscreen(WebDriverEvent event) {
        captureScreenShot(event);
	}

	@Override
	public void beforeMaximize(WebDriverEvent event) {
        captureScreenShot(event);
	}

	@Override
	public void beforeSetPosition(WebDriverEvent event, Point targetPosition) {
        captureScreenShot(event);
	}

	@Override
	public void beforeSetSizeByWindow(WebDriverEvent event, Dimension targetSize) {
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

	@Override
	public void onException(WebDriverEvent event, Cmd cmd, Throwable issue) {
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
        TestEvent testEvent = new TestEvent(event.toString(), 
                                            Level.INFO.toString(),
                                            event.getCmd().getLongCmdString(), 
                                            event.getParam1() + event.getParam2(), 
                                            event.getElementLocator(),
                                            event.getRecordNumber(), 
                                            file);
        taAdministrator.getTestCaseExecution().appendEvent(testEvent);
    }
}
