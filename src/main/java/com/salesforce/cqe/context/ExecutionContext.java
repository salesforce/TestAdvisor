package com.salesforce.cqe.context;

import java.util.List;

import com.salesforce.cqe.configuration.Configuration;

import com.salesforce.cqe.driver.listener.IEventListener;
import com.salesforce.cqe.reporter.IReporter;

import org.openqa.selenium.WebDriver;

/**
 * ExecutionContext class contains all test execution context objects during runtime.
 * This context will share between Manager and Listeners.
 * 
 * @author Yibing Tao
 */
public class ExecutionContext {

    private WebDriver driver;
    private IReporter reporter;
    private List<IEventListener> driverListeners;

    
    public ExecutionContext build(Configuration config) {
        //not implemented yet
        //create reporter and web driver
        throw new UnsupportedOperationException();
        
    }

    public IReporter getReporter(){
        return reporter;
    }

    public WebDriver getWebDriver(){
        return driver;
    }
    
}
