package com.salesforce.cqe.context;

import java.util.List;

import com.salesforce.cqe.configuration.Configuration;
import com.salesforce.cqe.driver.listener.EventListener;
import com.salesforce.cqe.provider.listener.TestListenerBase;
import com.salesforce.cqe.reporter.IReporter;

import org.openqa.selenium.WebDriver;

/**
 * @author Yibing Tao
 * ExecutionContext class contains all test execution context objects
 * during runtime.
 * This context will share between Providers and Listeners
 */
public class ExecutionContext implements IExecutionContext {

    private List<TestListenerBase> testListeners;
    private WebDriver driver;
    private IReporter reporter;
    private List<EventListener> driverListeners;
    
    @Override
    public ExecutionContext build(Configuration config) {
        throw new UnsupportedOperationException();
        
    }
    
}
