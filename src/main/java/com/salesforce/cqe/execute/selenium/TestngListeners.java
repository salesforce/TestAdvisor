package com.salesforce.cqe.execute.selenium;

import com.salesforce.cqe.common.HelperUtil;
import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static com.salesforce.cqe.common.HelperUtil.splunkQuery;

/**
 * @author shankysharma
 */
public class TestngListeners implements ITestListener, IConfigurationListener {


    /**
     * method will be called on test success
     * @param result ITestResult object with result details
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test pass: " + result.getClass().getName() + "." + result.getName());
        System.out.println("result: " + result.getStatus());
        HelperUtil.writingToFile(splunkQuery(result));
    }


    /**
     * method will be called on test failure
     * @param result ITestResult object with result information
     */
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test fail: " + result.getClass().getName() + "." + result.getName());
        HelperUtil.setTestFailureDetails.set(HelperUtil.setCurrentTimeInSplunkFormat() + " " + result.getThrowable());
        HelperUtil.writingToFile(splunkQuery(result));
    }

    /**
     * method will be called on test skipped
     * @param result ITestResult object with result information
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getClass().getName() + "." + result.getName());
        HelperUtil.setTestFailureDetails.set(result.getThrowable().toString() + " " + result.getSkipCausedBy());
        HelperUtil.writingToFile(splunkQuery(result));
    }

    /**
     * Invoked whenever a configuration method failed.
     * @param result ITestResult object with result information
     */
    @Override
    public void onConfigurationFailure(ITestResult result) {
        System.out.println("Configuration method fail: " + result.getClass().getName() + "." + result.getName());
        HelperUtil.setTestFailureDetails.set(HelperUtil.setCurrentTimeInSplunkFormat() + " " + result.getThrowable());
        HelperUtil.writingToFile(splunkQuery(result));
    }

    /**
     * Invoked whenever a configuration method skipped.
     * @param result ITestResult object with result information
     */
    @Override
    public void  onConfigurationSkip(ITestResult result) {
        System.out.println("Configuration method fail: " + result.getClass().getName() + "." + result.getName());
        HelperUtil.setTestFailureDetails.set(result.getThrowable().toString() + " " + result.getSkipCausedBy());
        HelperUtil.writingToFile(splunkQuery(result));
    }

}


