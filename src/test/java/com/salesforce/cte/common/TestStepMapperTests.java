/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import java.util.Map;

import com.salesforce.cte.common.TestStateMapper.TestState;

import org.testng.Assert;
import org.testng.annotations.Test;

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
			TestStateMapper.writeMapping(timeStamp + ".json");
		} catch (Exception e) {
			Assert.fail("writing test state map failed", e);
		}
		mappings.clear();
		try {
			mappings = TestStateMapper.getMapping(timeStamp + ".json");
		} catch (Exception e) {
			Assert.fail("reading test state map failed", e);
		}
		Assert.assertTrue(TestState.SKIPPED == mappings.get("outch"));
		Assert.assertTrue(TestState.FAILED == mappings.get("fail"));
		Assert.assertTrue(TestState.PASSED == mappings.get("passed"));
	}

}
