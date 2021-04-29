/**
 * 
 */
package com.salesforce.cqe.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.stream.MalformedJsonException;

/**
 * @author gneumann
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customer", "numOfScenarios", "numOfScenariosNotCovered", "allScenarios" })
public class BusinessScenarioMgr {
	public enum Status { 
		SUCCESS, FAILURE, SKIP, SUCCESS_PERCENTAGE_FAILURE, STARTED, UNKNOW;

		public static Status valueOftestNgStatus(int statusValue) {
			Status status = Status.UNKNOW;
			switch (statusValue) {
			case ITestResult.SUCCESS:
				status = Status.SUCCESS;
				break;
			case ITestResult.FAILURE:
				status = Status.FAILURE;
				break;
			case ITestResult.SKIP:
				status = Status.SKIP;
				break;
			case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
				status = Status.SUCCESS_PERCENTAGE_FAILURE;
				break;
			case ITestResult.STARTED:
				status = Status.STARTED;
				break;
			default:
			}
			return status;
		}
	}

	private static final String FILE_PREFIX = "business_scenarios_";
	private static final String FILE_TEMPLATE_POSTFIX = "template.json";
	private static final String FILE_RESULT_POSTFIX = "result.json";
	private static final String UNKNOWN_SCENARIO = "Unknown Scenario";

	@JsonProperty("customer")
	private String customer;
	@JsonProperty("numOfScenarios")
	private int numOfScenarios;
	@JsonProperty("numOfScenariosNotCovered")
	private int numOfScenariosNotCovered;
	@JsonProperty("allScenarios")
	private List<Scenario> allScenarios = null;

	@JsonIgnore
	private List<TestStatus> unknownScenario = new ArrayList<>();

	@JsonProperty("customer")
	public String getCustomer() {
		return customer;
	}

	@JsonProperty("customer")
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@JsonProperty("numOfScenarios")
	public int getNumOfScenarios() {
		return numOfScenarios;
	}

	@JsonProperty("numOfScenarios")
	public void setNumOfScenarios(int numOfScenarios) {
		this.numOfScenarios = numOfScenarios;
	}

	@JsonProperty("numOfScenariosNotCovered")
	public int getNumOfScenariosNotCovered() {
		return numOfScenariosNotCovered;
	}

	@JsonProperty("numOfScenariosNotCovered")
	public void setNumOfScenariosNotCovered(int numOfScenariosNotCovered) {
		this.numOfScenariosNotCovered = numOfScenariosNotCovered;
	}

	@JsonProperty("allScenarios")
	public List<Scenario> getAllScenarios() {
		return allScenarios;
	}

	@JsonProperty("allScenarios")
	public void setAllScenarios(List<Scenario> scenario) {
		this.allScenarios = scenario;
	}

	public BusinessScenarioMgr() {
	}

	public static BusinessScenarioMgr readTemplate() {
		return readTemplate(FILE_PREFIX + FILE_TEMPLATE_POSTFIX);
	}

	public static BusinessScenarioMgr readTemplate(String file) {
		BusinessScenarioMgr mgr = null;
		try {
			mgr = JsonHelper.toObject(file, BusinessScenarioMgr.class);
		} catch (MalformedJsonException e) {
			e.printStackTrace();
		}
		if (mgr != null) {
			int numOfScenarios = 0;
			for (Scenario scenario : mgr.getAllScenarios()) {
				if (!UNKNOWN_SCENARIO.equals(scenario.getScenarioName())) {
					numOfScenarios++;
				}
			}
			mgr.setNumOfScenarios(numOfScenarios);
		}
		
		return mgr;
	}

	public void saveResult() {
		updateNumOfScenariosNotCovered();
		addUnknownScenarioIfNeeded();
		try {
			JsonHelper.toFile("target/" + FILE_PREFIX + FILE_RESULT_POSTFIX, this);
		} catch (MalformedJsonException e) {
			e.printStackTrace();
		}
	}

	public void updateTestStatus(ITestResult result, Status status) {
		updateTestStatus(result.getMethod().getConstructorOrMethod().getMethod().getName(), status);
	}

	public void updateTestStatus(Method method, Status status) {
		updateTestStatus(method.getName(), status);
	}

	public void updateTestStatus(String testName, Status status) {
		boolean isTestStatusUpdated = false;
		for (Scenario scenario : getAllScenarios()) {
			List<TestStatus> allTests = scenario.getTests();
			if (allTests == null)
				allTests = new ArrayList<>();
			for (TestStatus test : allTests) {
				if (testName.equals(test.getTestName())) {
					test.setStatus(status.toString());
					isTestStatusUpdated = true;
					break;
				}
			}
			if (isTestStatusUpdated)
				break;
		}
		if (!isTestStatusUpdated) {
			unknownScenario.add(new TestStatus(testName, status.toString()));
		}
	}
	
	private void addUnknownScenarioIfNeeded() {
		boolean isScenarioFound = false;
		for (Scenario scenario : getAllScenarios()) {
			if (UNKNOWN_SCENARIO.equals(scenario.getScenarioName())) {
				scenario.setTests(unknownScenario);
				isScenarioFound = true;
				break;
			}
		}
		if (!isScenarioFound && !unknownScenario.isEmpty()) {
			Scenario unknownScenarioObj = new Scenario();
			unknownScenarioObj.setScenarioName(UNKNOWN_SCENARIO);
			unknownScenarioObj.setTests(unknownScenario);
			getAllScenarios().add(unknownScenarioObj);
		}
	}
	
	private void updateNumOfScenariosNotCovered() {
		for (Scenario scenario : getAllScenarios()) {
			boolean isScenarioCovered = false;
			if (!UNKNOWN_SCENARIO.equals(scenario.getScenarioName())) {
				for (TestStatus status : scenario.getTests()) {
					if ("SUCCESS".equals(status.getStatus())) {
						isScenarioCovered = true;
						break;
					}
				}
				if (!isScenarioCovered) {
					numOfScenariosNotCovered++;
				}
			}
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "scenarioName", "tests" })
	public static class Scenario implements Comparable<Scenario> {
		@JsonProperty("scenarioName")
		private String scenarioName;

		@JsonProperty("tests")
		private List<TestStatus> tests = null;

		@JsonProperty("scenarioName")
		public String getScenarioName() {
			return scenarioName;
		}

		@JsonProperty("scenarioName")
		public void setScenarioName(String scenarioName) {
			this.scenarioName = scenarioName;
		}

		@JsonProperty("tests")
		public List<TestStatus> getTests() {
			return tests;
		}

		@JsonProperty("tests")
		public void setTests(List<TestStatus> tests) {
			this.tests = tests;
		}

		@Override
		public int compareTo(Scenario o) {
			return scenarioName.compareTo(o.getScenarioName());
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Scenario)) {
				return false;
			}
			return scenarioName.equals(((Scenario) o).getScenarioName());
		}
		
		@Override
		public int hashCode() {
			return scenarioName.hashCode();
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "testName", "status" })
	public static final class TestStatus implements Comparable<TestStatus> {
		@JsonProperty("testName")
		private String testName;
		@JsonProperty("status")
		private String status;

		public TestStatus() {
		}

		public TestStatus(String testName, String status) {
			if (testName == null || testName.length() == 0)
				throw new IllegalArgumentException("Test case name must not be null or empty");
			this.testName = testName;
			this.status = status;
		}

		@JsonProperty("testName")
		public String getTestName() {
		return testName;
		}

		@JsonProperty("testName")
		public void setTestName(String testName) {
		this.testName = testName;
		}

		@JsonProperty("status")
		public String getStatus() {
		return status;
		}

		@JsonProperty("status")
		public void setStatus(String status) {
		this.status = status;
		}

		@Override
		public int compareTo(TestStatus o) {
			return testName.compareTo(o.getTestName());
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TestStatus)) {
				return false;
			}
			return testName.equals(((TestStatus) o).getTestName());
		}
		
		@Override
		public int hashCode() {
			return testName.hashCode();
		}
	}
}
