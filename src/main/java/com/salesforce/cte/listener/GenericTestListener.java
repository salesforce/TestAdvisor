/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.salesforce.cte.admin.TestAdvisorAdministrator;
import com.salesforce.cte.common.TestCaseExecution;
import com.salesforce.cte.common.TestEvent;
import com.salesforce.cte.common.TestStatus;

/**
 * Base class for all test listeners
 * 
 * @author Yibing Tao
 */
public class GenericTestListener {
    protected static final Logger LOGGER = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

    // Singleton TestAdvisorAdministrator
	private TestAdvisorAdministrator administrator;

    public GenericTestListener(){
        administrator = TestAdvisorAdministrator.getInstance();
    }

    /**
     * Callback when a test run starts
     */
    public void onTestRunStart(){
		administrator.startTestRun();
    }

    /**
     * Callbak when a test run ends
     */
    public void onTestRunEnd(){
        administrator.endTestRun();
		try {
			administrator.saveTestResult();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
    }

    /**
     * Callback when a test case starts
     * @param testName test case name
     */
    public void onTestCaseStart(String testName){
        // Initialize TestCaseExecution object
        administrator.createTestCaseExecution(testName);
    }

    /**
     * Callback when a test case ends
     */
    public void onTestCaseEnd(){
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		testCaseExecution.saveEndTime();
    }

    /**
     * Callback when a test case configuration method starts, such as test setup/teardown
     * @param testName test case name
     */
    public void onTestConfigurationStart(String testName){
        TestCaseExecution testCaseExecution = administrator.createTestCaseExecution(testName);
        testCaseExecution.setConfiguration(true);
    }

    /**
     * Callback when a test case configuration method ends, such as test setup/teardown
     * treat it same as test case ends
     * 
     */
    public void onTestConfigurationEnd(){
        onTestCaseEnd();
    }

    /**
     * Callback when a test event happens
     * @param eventContent event content
     * @param level event level
     */
    public void onTestCaseEvent(String eventContent, Level level){
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
        testCaseExecution.appendEvent(new TestEvent(eventContent, level.toString()));
    }

    /**
     * Callback when a test case status changes
     * @param status test status
     */
    public void onTestCaseStatus(TestStatus status){
        TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		testCaseExecution.setTestStatus(status);
    }

}
