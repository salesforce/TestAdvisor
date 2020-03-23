package com.salesforce.cqe.execute.selenium;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
 
/**
 * Helper class for determining if a test should be re-run or not.
 * 
 * @author gneumann
 */
public class TestRetryCounter implements IRetryAnalyzer {
    private static final int MAXIMUM_RETRIES = 2;
    private int count = 0;
 
    /**
     * Re-run failed tests up to n times.
     * @param testResult test status information to determine if it has failed
     * @returns true if test is to be re-run, false otherwise
     */
    @Override
    public boolean retry(ITestResult testResult) {
        if (testResult.isSuccess()) {
        	// test has passed, so no rerun required
            testResult.setStatus(ITestResult.SUCCESS);
        	return false;
        }

        // test has failed
        if (count < MAXIMUM_RETRIES) {
        	// mark this test run as failure
            testResult.setStatus(ITestResult.FAILURE);
            testResult.setWasRetried(true);
            count++;
            // have it re-run
            return true;
        }
        // exceeded maximum time of retries
        testResult.setStatus(ITestResult.FAILURE);
        testResult.setWasRetried(true);
        return false;
    }
}