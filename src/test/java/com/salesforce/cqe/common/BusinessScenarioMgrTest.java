/**
 * 
 */
package com.salesforce.cqe.common;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.salesforce.cqe.common.BusinessScenarioMgr.Status;

/**
 * @author gneumann
 *
 */
public class BusinessScenarioMgrTest {

	@Test
	public void testUpdateTestStatus() {
		BusinessScenarioMgr mgr = BusinessScenarioMgr.readTemplate("test-resources/business_scenarios_template.json");
		Assert.assertNotNull(mgr);
		
		mgr.updateTestStatus("My second test", Status.SUCCESS);
		mgr.updateTestStatus("My fifth test", Status.SUCCESS);
		mgr.saveResult();
	}
}
