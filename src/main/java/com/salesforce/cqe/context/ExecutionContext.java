package com.salesforce.cqe.context;

import java.util.List;

import com.salesforce.cqe.configuration.ConfigurationBase;
import com.salesforce.cqe.driver.listener.WebDriverListenerBase;
import com.salesforce.cqe.provider.listener.TestListenerBase;
import com.salesforce.cqe.reporter.IReporter;

import org.openqa.selenium.WebDriver;

public class ExecutionContext implements IExecutionContext {

    private List<TestListenerBase> testListeners;
    private WebDriver driver;
    private IReporter reporter;
    private List<WebDriverListenerBase> driverListeners;
    
    @Override
    public ExecutionContext build(ConfigurationBase config) {
        throw new UnsupportedOperationException();
        
    }
    
}
