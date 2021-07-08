package com.salesforce.cqe.admin;

/**
 * This enum defines the various statuses a test can possibly have:
 * 
 * 1) PASSED: When the current test being executed passes (in other words it passes all testing criteria)
 * 2) FAILED: When the current test being executed fails
 * 3) SKIPPED: When the current test wasn't executed (in other words it fails within the setUp() function)
 * 
 * Failed (JUnit) --> Failed (TestNG) --> FAILED
 * Successful (JUnit) --> Passed (TestNG) --> PASSED
 * Aborted (JUnit) --> SKIPPED
 * FailedWithinSuccess (TestNG) --> WILL NOT BE MAPPED
 *
 * https://junit.org/junit5/docs/5.0.1/api/org/junit/platform/engine/TestExecutionResult.Status.html
 * https://testng.org/doc/documentation-main.html#test-results
 * 
 * @author gpahuja
 *
 */
public enum TestStatus {
    PASSED,
    FAILED, 
    SKIPPED
}