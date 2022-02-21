/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener.junit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.salesforce.cte.admin.TestAdvisorAdministrator;
import com.salesforce.cte.common.TestCaseExecution;
import com.salesforce.cte.common.TestEvent;
import com.salesforce.cte.common.TestStatus;

/**
 * Listener will be triggered by test provider, collect execution data and use
 * TestAdvisorAdministrator to save the data. test listener will collect
 * detailed test execution information, such as exact timestamp, error message
 * and exceptions.
 * 
 * @author gneumann
 *
 */
public class TestListenerJUnit4 extends org.junit.runner.notification.RunListener {
	private static final Logger LOGGER = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

	// Singleton TestAdvisorAdministrator
	private TestAdvisorAdministrator administrator;

	/**
     * {@inheritDoc}
     */
	@Override
    public void testRunStarted(Description description) throws Exception {
		// Invoked before the JUnit run starts.
		administrator = TestAdvisorAdministrator.getInstance();
		administrator.startTestRun();
    }

	/**
     * {@inheritDoc}
     */
	@Override
    public synchronized void testRunFinished(Result result) throws Exception {
		// Invoked once all tests have been run.
		administrator.endTestRun();
		try {
			administrator.saveTestResult();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.toString());
		}
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public void testStarted(Description description) throws Exception {
		// Initialize TestCaseExecution object
		administrator.createTestCaseExecution(description.getClassName() + "." + description.getMethodName());
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public void testFinished(Description description) throws Exception {
		TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		// test status is already set to correct value 
		// i.e. don't set it here to PASS as it may override a FAIL
		testCaseExecution.saveEndTime();
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void testFailure(Failure failure) throws Exception {
		TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		testCaseExecution.setTestStatus(TestStatus.FAILED);
		testCaseExecution.appendEvent(new TestEvent(failure.toString(), Level.SEVERE.toString()));
		testCaseExecution.saveEndTime();
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void testIgnored(Description description) throws Exception {
		// Invoked each time a test is skipped.
		TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		testCaseExecution.setTestStatus(TestStatus.SKIPPED);
		testCaseExecution.saveEndTime();
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public void testAssumptionFailure(Failure failure) {
		// Invoked each time a test is skipped.
		TestCaseExecution testCaseExecution = administrator.getTestCaseExecution();
		testCaseExecution.setTestStatus(TestStatus.SKIPPED);
		testCaseExecution.saveEndTime();
    }
}
