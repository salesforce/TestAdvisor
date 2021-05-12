package com.salesforce.cqe.context;

import com.salesforce.cqe.configuration.Configuration;
import com.salesforce.cqe.reporter.IReporter;

import org.openqa.selenium.WebDriver;

/**
 * @author Yibing Tao
 * ExecutionContext class contains all test execution context objects
 * during runtime.
 * This context will share between Providers and Listeners
 */
public class ExecutionContext {

    private WebDriver driver;
    private IReporter reporter;
    
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
