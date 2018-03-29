/**
 * 
 */
package com.salesforce.cqe.statistics;

import org.testng.util.Strings;

/**
 * @author gneumann
 *
 */
public class Result {
	public enum State { PASS, FAIL };
	
	private final String customerSuite;
	private final String testName;
	private final int buildId;
	private final State state;
	private final long timeElapsedInMillisecs;
	
	public Result(
			String customerSuite,
			String testName,
			int buildId,
			State state,
			long timeElapsedInMillisecs) throws IllegalArgumentException {
		if (Strings.isNotNullAndNotEmpty(customerSuite)) {
			this.customerSuite = customerSuite; 
		} else {
			throw new IllegalArgumentException("customerSuite must not be null or empty!");
		}
		if (Strings.isNotNullAndNotEmpty(testName)) {
			this.testName = testName;
		} else {
			throw new IllegalArgumentException("testName must not be null or empty!");
		}
		if (buildId > 0) {
			this.buildId = buildId;
		} else {
			throw new IllegalArgumentException("buildId must be > 0!");
		}
		if (state != null) {
			this.state = state;
		} else {
			throw new IllegalArgumentException("state must not be null!");
		}
		if (timeElapsedInMillisecs > 0) {
			this.timeElapsedInMillisecs = timeElapsedInMillisecs;
		} else {
			throw new IllegalArgumentException("timeElapsedInMillisecs must be > 0!");
		}
	}

	public String getCustomerSuite() {
		return customerSuite;
	}

	public String getTestName() {
		return testName;
	}

	public int getBuildId() {
		return buildId;
	}

	public State getState() {
		return state;
	}

	public long getTimeElapsedInMillisecs() {
		return timeElapsedInMillisecs;
	}
}
