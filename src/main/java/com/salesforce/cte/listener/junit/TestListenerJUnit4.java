/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener.junit;

import java.util.logging.Level;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.salesforce.cte.common.TestStatus;
import com.salesforce.cte.listener.GenericTestListener;

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
    GenericTestListener genericListener = new GenericTestListener();
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void testRunStarted(Description description) throws Exception {
        // Invoked before the JUnit run starts.
        genericListener.onTestRunStart();
    }

	/**
     * {@inheritDoc}
     */
	@Override
    public synchronized void testRunFinished(Result result) throws Exception {
        // Invoked once all tests have been run.
        genericListener.onTestRunEnd();
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public void testStarted(Description description) throws Exception {
        // Initialize TestCaseExecution object
        genericListener.onTestCaseStart(description.getClassName() + "." + description.getMethodName());
    }

    /**
     * {@inheritDoc}
     */
	@Override
  public void testFinished(Description description) throws Exception {
		genericListener.onTestCaseEnd();
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void testFailure(Failure failure) throws Exception {
        genericListener.onTestCaseStatus(TestStatus.FAILED);
        genericListener.onTestCaseEvent(failure.toString(), Level.SEVERE);
        genericListener.onTestCaseEnd();
    }
	
    /**
     * {@inheritDoc}
     */
	@Override
    public void testIgnored(Description description) throws Exception {
		// Invoked each time a test is skipped.
		genericListener.onTestCaseStatus(TestStatus.SKIPPED);
		genericListener.onTestCaseEnd();
    }

    /**
     * {@inheritDoc}
     */
	@Override
    public void testAssumptionFailure(Failure failure) {
		// Invoked each time a test is skipped.
		genericListener.onTestCaseStatus(TestStatus.SKIPPED);
		genericListener.onTestCaseEnd();
    }
}
