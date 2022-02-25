/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.listener.testng;

import org.testng.ITestListener;
import org.testng.IConfigurationListener;
import org.testng.IConfigurationListener2;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.logging.Level;

import com.salesforce.cte.common.TestStatus;
import com.salesforce.cte.listener.GenericTestListener;

/**
 * Listener will be triggered by test provider, collect execution data and use
 * TestAdvisorAdministrator to save the data. test listener will collect
 * detailed test execution information, such as exact timestamp, error message
 * and exceptions
 * 
 * @author Yibing Tao
 */
public class TestListener implements ITestListener, IExecutionListener, IConfigurationListener, IConfigurationListener2 {
	//add IConfigurationListener2 for TestNG 6 compatibility
	
	GenericTestListener genericListener = new GenericTestListener();

	// IExecutionListener
	@Override
	public void onExecutionStart() {
		// Invoked before the TestNG run starts.
		genericListener.onTestRunStart();
	}

	@Override
	public void onExecutionFinish() {
		// Invoked once all the suites have been run.
		genericListener.onTestRunEnd();
	}

	// IConfigurationListener
	@Override
	public void beforeConfiguration(ITestResult result) {
		genericListener.onTestConfigurationStart(result.getTestClass().getName() + "." + result.getName());
	}

	@Override
	public void onConfigurationFailure(ITestResult result) {
		// Invoked whenever a configuration method failed.
		genericListener.onTestCaseStatus(TestStatus.FAILED);
		genericListener.onTestCaseEvent(result.toString(), Level.SEVERE);
		genericListener.onTestConfigurationEnd();
	}

	@Override
	public void onConfigurationSkip(ITestResult result) {
		// Invoked whenever a configuration method was skipped.
		genericListener.onTestCaseStatus(TestStatus.SKIPPED);
		genericListener.onTestConfigurationEnd();
	}

	@Override
	public void onConfigurationSuccess(ITestResult result) {
		// Invoked whenever a configuration method succeeded.
		genericListener.onTestCaseStatus(TestStatus.PASSED);
		genericListener.onTestConfigurationEnd();
	}

	// ITestListener
	@Override
	public void onStart(ITestContext context) {
		// Invoked after the test class is instantiated and before any configuration
		// method is called.
	}

	@Override
	public void onFinish(ITestContext context) {
		// Invoked after all the tests have run and all their Configuration methods have
		// been called.
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Invoked each time before a test will be invoked.
		genericListener.onTestCaseStart(result.getTestClass().getName() + "." + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Invoked each time a test fails.
		genericListener.onTestCaseStatus(TestStatus.FAILED);
		String eventContent = result.toString();
		if (result.getThrowable() != null) {
			eventContent += result.getThrowable().toString();
		}
		genericListener.onTestCaseEvent(eventContent, Level.SEVERE);
		genericListener.onTestCaseEnd();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// Invoked each time a test succeeds.
		genericListener.onTestCaseStatus(TestStatus.PASSED);
		genericListener.onTestCaseEnd();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// Invoked each time a test is skipped.
		genericListener.onTestCaseStatus(TestStatus.SKIPPED);
		genericListener.onTestCaseEnd();
	}
}
