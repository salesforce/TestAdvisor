/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener.junit;

import com.salesforce.cte.common.TestStatus;
import com.salesforce.cte.listener.GenericTestListener;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestExecutionResult.Status;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class TestListenerJUnit5 implements TestExecutionListener {
    GenericTestListener genericListener = new GenericTestListener();
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan){
        genericListener.onTestRunStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan){
        genericListener.onTestRunEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        genericListener.onTestCaseStatus(TestStatus.SKIPPED);
		genericListener.onTestCaseEnd();
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        genericListener.onTestCaseStart(testIdentifier.getDisplayName());
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == Status.FAILED ){
            genericListener.onTestCaseStatus(TestStatus.FAILED);
		    genericListener.onTestCaseException(testExecutionResult.getThrowable().orElse(new Throwable()));
        }

        genericListener.onTestCaseEnd();
    }
 
}
