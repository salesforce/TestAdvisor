/**
 * 
 */
package com.salesforce.cqe.common;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gneumann
 *
 */
public class TestStateMapper {
	private static long timeStamp = System.currentTimeMillis();

	public enum TestState {
		PASSED, FAILED, SKIPPED; 
	}

	@JsonProperty("mappings")
	private static Map<String, TestState> mappings = new HashMap<>();
	
	@JsonProperty("mappings")
	public static Map<String, TestState> getMappings() {
		return mappings;
	}

	@JsonProperty("mappings")
	public static void setMappings(Map<String, TestState> mappings) {
		TestStateMapper.mappings = mappings;
	}
	
	public static void main(String[] args) {
		mappings.put("passed", TestState.PASSED);
		mappings.put("fail", TestState.FAILED);
		mappings.put("outch", TestState.SKIPPED);
		try {
			createMappingFile();
		} catch (Exception e1) {
			Assert.fail("writing test state map failed", e1);
		}
		mappings.clear();
		try {
			readMappingFile();
		} catch (Exception e) {
			Assert.fail("reading test state map failed", e);
		}
		Assert.assertTrue(TestState.SKIPPED == mappings.get("outch"));
		Assert.assertTrue(TestState.FAILED == mappings.get("fail"));
		Assert.assertTrue(TestState.PASSED == mappings.get("passed"));
	}

	private static void createMappingFile() throws Exception {
		writeMapping("test-output/state" + timeStamp + ".json");
	}

	private static void readMappingFile() throws Exception {
		getMapping("test-output/state" + timeStamp + ".json");
	}
	
	/**
	 * Reads JSON file containing state mapping from given path.
	 * @param fileName file to open, including relative or absolute path
	 * @return TestStateMapper instance
	 * @throws Exception if .json file is not readable.
	 */
	public static Map<String, TestState> getMapping(String fileName) throws Exception {
		if (mappings.isEmpty()) {
			@SuppressWarnings("unchecked")
			Map<String, String> tempMap = (Map<String, String>) JsonHelper.toObject(fileName, Map.class);
			for (String key : tempMap.keySet()) {
				String value = tempMap.get(key);
				mappings.put(key, TestState.valueOf(value));
			}
		}
		return mappings;
	}

	/**
	 * Writes JSON file containing state mapping.
	 * @param fileName file to write, including relative or absolute path
	 * @throws Exception if .json file is not writable.
	 */
	public static void writeMapping(String fileName) throws Exception {
		JsonHelper.toFile(fileName, mappings);
	}
}
