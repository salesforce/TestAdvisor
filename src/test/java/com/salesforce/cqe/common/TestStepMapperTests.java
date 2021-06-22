/**
 * 
 */
package com.salesforce.cqe.common;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.salesforce.cqe.common.TestStateMapper.TestState;

/**
 * @author gneumann
 *
 */
class TestStepMapperTests {

	@Test
	void createAndReadTestStateDefinitionFile() {
		long timeStamp = System.currentTimeMillis();
		
		Map<String, TestState> mappings = TestStateMapper.getMappings();

		mappings.put("passed", TestState.PASSED);
		mappings.put("fail", TestState.FAILED);
		mappings.put("outch", TestState.SKIPPED);
		try {
			TestStateMapper.writeMapping("test-output/state" + timeStamp + ".json");
		} catch (Exception e) {
			Assert.fail("writing test state map failed", e);
		}
		mappings.clear();
		try {
			mappings = TestStateMapper.getMapping("test-output/state" + timeStamp + ".json");
		} catch (Exception e) {
			Assert.fail("reading test state map failed", e);
		}
		Assert.assertTrue(TestState.SKIPPED == mappings.get("outch"));
		Assert.assertTrue(TestState.FAILED == mappings.get("fail"));
		Assert.assertTrue(TestState.PASSED == mappings.get("passed"));
	}

}
