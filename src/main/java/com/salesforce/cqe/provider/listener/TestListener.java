package com.salesforce.cqe.provider.listener;

import org.testng.ITestListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.salesforce.cqe.context.ExecutionContext;
import com.salesforce.cqe.manager.ExecutionManager;



/**
 * @author Yibing Tao
 * Listener will be triggered by test provider, collect execution data
 * and use reporter to save the data.
 * test listener will collect detailed test execution information, 
 * such as exact timestamp, error message and execeptions
 */
public class TestListener implements ITestListener {
    
    private ExecutionContext executionContext = ExecutionManager.getExecutionContext();

    @Override
    public void onStart(ITestContext context){
        //not implemented yet
        //collect data before test starts
        executionContext.getReporter().log();
    }

    @Override
    public void onFinish(ITestContext context){
        //not implemented yet
        //collect data before test starts
    }

    @Override
    public void onTestFailure(ITestResult result){
        //not implmented yet
        //collect data when test failed
    }

    @Override
    public void onTestSuccess(ITestResult result){
        //not implmented yet
        //collect data when test succeed
    }

    @Override
    public void onTestSkipped(ITestResult result){
        //not implmented yet
        //collect data when test skipped
    }
}
