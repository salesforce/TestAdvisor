/**
 * 
 */
package com.salesforce.cqe.common;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.google.gson.stream.MalformedJsonException;

/**
 * @author gneumann
 *
 */
public class TestContextTest {
	@Test
	public void testOpeningFileWithTwoProductionOrgs() {
		TestContext context = null;
		try {
			context = TestContext.getContext("test-resources/testcontext_with_two_prod_orgs.json");
		} catch (MalformedJsonException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull("Unable to retrieve 2nd production org name", context.getProductionOrgId2());
	}

	@Test
	public void testGettingSource2() {
		TestContext context = null;
		try {
			context = TestContext.getContext("test-resources/testcontext_with_two_prod_orgs.json");
		} catch (MalformedJsonException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull("Unable to retrieve 2nd source org", context.getOrgs().getSource2());
		Assert.assertNotNull("Unable to retrieve 2nd source org id", context.getOrgs().getSource2().getSandboxOrgId());
	}

	@Test
	public void testOpeningFileWithOneProductionOrg() {
		TestContext context = null;
		try {
			context = TestContext.getContext("test-resources/testcontext_with_one_prod_org.json");
		} catch (MalformedJsonException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertNull("Failure during retrieval of non-existing 2nd production org name", context.getProductionOrgId2());
	}
}
