package com.salesforce.cqe.execute;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author Yibing Tao
 */
public class EnhancedTestngListener extends TestListenerAdapter implements IInvokedMethodListener{
    //Enhanced testng listener write test result and selenium log with timestamps

    @Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
    
    }

    @Override
	public void onTestFailure(ITestResult testResult) {
        
    }
}
