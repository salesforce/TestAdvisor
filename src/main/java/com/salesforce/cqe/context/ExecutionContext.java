package com.salesforce.cqe.context;

import java.util.List;

import com.salesforce.cqe.configuration.Configuration;
import com.salesforce.cqe.driver.listener.IEventListener;
import com.salesforce.cqe.provider.listener.ITestListener;
import com.salesforce.cqe.reporter.IReporter;

import org.openqa.selenium.WebDriver;

/**
 * ExecutionContext class contains all test execution context objects during runtime.
 * This context will share between Providers and Listeners.
 * 
 * @author Yibing Tao
 */
public class ExecutionContext implements IExecutionContext {

    private List<ITestListener> testListeners;
    private WebDriver driver;
    private IReporter reporter;
    private List<IEventListener> driverListeners;
    
    @Override
    public ExecutionContext build(Configuration config) {
        throw new UnsupportedOperationException();
        
    }
    
}
