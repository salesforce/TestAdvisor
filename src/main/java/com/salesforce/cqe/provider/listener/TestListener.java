package com.salesforce.cqe.provider.listener;

import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.IConfigurationListener;
import org.testng.IConfigurationListener2;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.logging.Level;

import com.salesforce.cqe.admin.DrillBitAdministrator;
import com.salesforce.cqe.admin.TestEvent;
import com.salesforce.cqe.admin.TestCaseExecution;
import com.salesforce.cqe.admin.TestStatus;



/**
 * @author Yibing Tao
 * Listener will be triggered by test provider, collect execution data
 * and use DrillBitAdministrator to save the data.
 * test listener will collect detailed test execution information, 
 * such as exact timestamp, error message and execeptions
 */
public class TestListener implements ITestListener, IExecutionListener, IConfigurationListener, IConfigurationListener2{

    //Singlton DrillBitAdministrator
    private DrillBitAdministrator administrator;

    //IConfigurationListener2
    @Override
    public void beforeConfiguration(ITestResult result){
        //Invoked before a configuration method is invoked.
        TestCaseExecution testCaseExecution = getCurrentTestCaseExecution(result);
        testCaseExecution.appendEvent(new TestEvent(result.getName(), "",Level.INFO) );
    }
    
    //IConfigurationListener
    @Override
    public void onConfigurationFailure(ITestResult result){     
        //Invoked whenever a configuration method failed.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        setConfigurationStatus(result, TestStatus.FAILED);
        //append failure event with failed method name and exception class name
        if (result.getThrowable() != null){
            testCaseExecution.appendEvent(new TestEvent(
                result.getName(), result.getThrowable().getClass().getName(),Level.SEVERE) );
        }
        testCaseExecution.saveEndTime();
    }

    @Override
    public void onConfigurationSkip(ITestResult result){       
        //Invoked whenever a configuration method was skipped.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        setConfigurationStatus(result, TestStatus.SKIPPED);
        testCaseExecution.appendEvent(new TestEvent(result.getName(), "",Level.WARNING));
        testCaseExecution.saveEndTime();
    }
    
    @Override
    public void onConfigurationSuccess(ITestResult result){     
        //Invoked whenever a configuration method succeeded.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        setConfigurationStatus(result, TestStatus.PASSED);
        testCaseExecution.appendEvent(new TestEvent(result.getName(), "",Level.INFO));
        testCaseExecution.saveEndTime();
    }

    /**
     * Get current test case execution object, create new one for new test case
     * @param result
     * TestNG test result object
     * @return
     * current test case execution object
     */
    private TestCaseExecution getCurrentTestCaseExecution(ITestResult result){
        TestCaseExecution current = administrator.getTestCaseExecution();
        if (current == null || 
            result.getMethod().isBeforeClassConfiguration() || 
            result.getMethod().isBeforeTestConfiguration() ||
            result.getMethod().isAfterClassConfiguration() ||
            result.getMethod().isAfterTestConfiguration() ||
            (result.getMethod().isBeforeMethodConfiguration() && !current.isBeforeMethod()) || //first beforeMethod only
            result.getMethod().isTest() && !current.isBeforeMethod() ){ //Test without beforeMethod
                current = administrator.createTestCaseExecution();
                current.setTestName(result.getTestClass().getName()+"."+result.getName());
        }
        if(result.getMethod().isBeforeMethodConfiguration())
            current.setBeforeMethod();
        else    
            current.unsetBeforeMethod();
        return current;
    }

    /**
     * Set test configruation status based on configuration type
     * @param result 
     * testng result object
     * @param status
     * Test status
     */
    private void setConfigurationStatus(ITestResult result, TestStatus status){
        TestCaseExecution current = administrator.getTestCaseExecution();
        ITestNGMethod method = result.getMethod();
        if (method.isBeforeClassConfiguration() ||
            method.isBeforeTestConfiguration() ||
            method.isAfterClassConfiguration() ||
            method.isAfterTestConfiguration() ){
                current.setTestStatus(status);
            }
    }

    //ITestListener
    @Override
    public void onStart(ITestContext context){
        //Invoked after the test class is instantiated and before any configuration method is called.
    }

    @Override
    public void onFinish(ITestContext context){
        //Invoked after all the tests have run and all their Configuration methods have been called.
    }

    @Override
    public void onTestFailure(ITestResult result){     
        //Invoked each time a test fails.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        testCaseExecution.setTestStatus(TestStatus.FAILED);
        //append failure event with failed method name and exception class name
        if (result.getThrowable() != null){
            testCaseExecution.appendEvent(new TestEvent(
                result.getName(), result.getThrowable().getClass().getName(),Level.SEVERE));
        }
        testCaseExecution.saveEndTime();
    }

    @Override
    public void onTestSuccess(ITestResult result){      
        //Invoked each time a test succeeds.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        testCaseExecution.setTestStatus(TestStatus.PASSED);
        testCaseExecution.saveEndTime();
    }

    @Override
    public void onTestSkipped(ITestResult result){       
        //Invoked each time a test is skipped.
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        testCaseExecution.setTestStatus(TestStatus.SKIPPED);
        testCaseExecution.saveEndTime();
    }

    @Override
    public void onTestStart(ITestResult result){  
        //Invoked each time before a test will be invoked.
        TestCaseExecution testCaseExecution = getCurrentTestCaseExecution(result);
        //save test name
        testCaseExecution.setTestName(result.getTestClass().getName()+"."+result.getName());
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
