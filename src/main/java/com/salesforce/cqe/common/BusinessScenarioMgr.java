/**
 * 
 */
package com.salesforce.cqe.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.stream.MalformedJsonException;

/**
 * @author gneumann
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customer", "numOfScenarios", "allScenarios" })
public class BusinessScenarioMgr {
	private static final String FILE_PREFIX = "business_scenarios_";
	private static final String FILE_TEMPLATE_POSTFIX = "template.json";
	private static final String FILE_RESULT_POSTFIX = "result.json";

	@JsonProperty("customer")
	private String customer;
	@JsonProperty("numOfScenarios")
	private int numOfScenarios;
	@JsonProperty("allScenarios")
	private AllScenarios allScenarios;

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

	@JsonProperty("allScenarios")
	public AllScenarios getAllScenarios() {
		return allScenarios;
	}

	@JsonProperty("allScenarios")
	public void setAllScenarios(AllScenarios allScenarios) {
		this.allScenarios = allScenarios;
	}

	public BusinessScenarioMgr() {
	}

	public static BusinessScenarioMgr readTemplate() {
		BusinessScenarioMgr mgr = null;
		try {
			mgr = JsonHelper.toObject(FILE_PREFIX + FILE_TEMPLATE_POSTFIX, BusinessScenarioMgr.class);
		} catch (MalformedJsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mgr;
	}

	public void saveResult() {
		try {
			JsonHelper.toFile("target/" + FILE_PREFIX + FILE_RESULT_POSTFIX, this);
		} catch (MalformedJsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "scenario" })
	public static class AllScenarios {

		@JsonProperty("scenario")
		private List<Scenario> scenario = null;

		@JsonProperty("scenario")
		public List<Scenario> getScenario() {
			return scenario;
		}

		@JsonProperty("scenario")
		public void setScenario(List<Scenario> scenario) {
			this.scenario = scenario;
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
