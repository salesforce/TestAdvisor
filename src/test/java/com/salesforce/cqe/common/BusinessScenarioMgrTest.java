/**
 * 
 */
package com.salesforce.cqe.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.google.gson.stream.MalformedJsonException;
import com.salesforce.cqe.common.BusinessScenarioMgr.AllScenarios;
import com.salesforce.cqe.common.BusinessScenarioMgr.Scenario;
import com.salesforce.cqe.common.BusinessScenarioMgr.TestStatus;

/**
 * @author gneumann
 *
 */
public class BusinessScenarioMgrTest {
	@Test
	public void testWritingResult() {
		BusinessScenarioMgr mgr = new BusinessScenarioMgr();
		mgr.setCustomer("My Test Customer");
		mgr.setNumOfScenarios(2);
		AllScenarios allScenarios = new AllScenarios();
		mgr.setAllScenarios(allScenarios);
		List<Scenario> scenariosToBeAdded = new ArrayList<>();
		allScenarios.setScenario(scenariosToBeAdded);
		
		List<TestStatus> testStatusToBeAdded1 = new ArrayList<>();
		Scenario scenario1 = new Scenario();
		scenario1.setScenarioName("Scenario 1");
		scenario1.setTests(testStatusToBeAdded1);

		List<TestStatus> testStatusToBeAdded2 = new ArrayList<>();
		Scenario scenario2 = new Scenario();
		scenario2.setScenarioName("Scenario 2");
		scenario2.setTests(testStatusToBeAdded2);

		testStatusToBeAdded1.add(new TestStatus("My first test", "pass"));
		testStatusToBeAdded1.add(new TestStatus("My second test", "fail"));
		testStatusToBeAdded2.add(new TestStatus("My third test", "pass"));
		testStatusToBeAdded2.add(new TestStatus("My fourth test", "fail"));
		
		scenariosToBeAdded.add(scenario1);
		scenariosToBeAdded.add(scenario2);

		mgr.saveResult();
	}

	@Test
	public void testReadingTemplate() {
		BusinessScenarioMgr mgr = null;
		try {
			mgr = JsonHelper.toObject("test-resources/business_scenarios_template.json", BusinessScenarioMgr.class);
		} catch (MalformedJsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(mgr);
	}
}
