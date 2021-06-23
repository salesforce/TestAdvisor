package com.salesforce.cqe.provider.listener;

import org.testng.ITestListener;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.salesforce.cqe.admin.DrillBitAdministrator;
import com.salesforce.cqe.admin.TestCaseExecution;
import com.salesforce.cqe.driver.listener.Event;


/**
 * @author Yibing Tao
 * Listener will be triggered by test provider, collect execution data
 * and use reporter to save the data.
 * test listener will collect detailed test execution information, 
 * such as exact timestamp, error message and execeptions
 */
public class TestListener implements ITestListener, IExecutionListener {

    DrillBitAdministrator administrator;

    //ITestListener
    @Override
    public void onStart(ITestContext context){
        //Invoked after the test class is instantiated and before any configuration method is called.
        administrator.createTestCaseExecution();
    }

    @Override
    public void onFinish(ITestContext context){
        //Invoked after all the tests have run and all their Configuration methods have been called.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        testCaseExecution.saveEndTime();
    }

    @Override
    public void onTestFailure(ITestResult result){
        //Invoked each time a test fails.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        //append failure event
        testCaseExecution.appendEvent(new Event());
    }

    @Override
    public void onTestSuccess(ITestResult result){
        //Invoked each time a test succeeds.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        //append success event
        testCaseExecution.appendEvent(new Event());
    }

    @Override
    public void onTestSkipped(ITestResult result){
        //Invoked each time a test is skipped.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        //append skip event
        testCaseExecution.appendEvent(new Event());
    }

    @Override
    public void onTestStart(ITestResult result){
        //Invoked each time before a test will be invoked.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        //append start event
        testCaseExecution.appendEvent(new Event());
        //save test name
    }

    //IExecutionListener 
    @Override
    public void onExecutionStart(){
        //Invoked before the TestNG run starts.
        administrator = DrillBitAdministrator.getInstance();
    }

    @Override
    public void onExecutionFinish(){
        //Invoked once all the suites have been run.
        administrator.saveTestCaseExecutionList();
    }
}
