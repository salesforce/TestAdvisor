/**
 * 
 */
package com.salesforce.cte.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author gneumann
 *
 */
public class TestStateMapper {
	public enum TestState {
		PASSED,
		FAILED,
		SKIPPED; 
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

	/**
	 * Reads JSON file containing state mapping from given path.
	 * @param fileName file to open, including relative or absolute path
	 * @return TestStateMapper instance
	 * @throws Exception if .json file is not readable.
	 */
	public static Map<String, TestState> getMapping(String fileName) throws Exception {
		if (mappings.isEmpty()) {
			if (new File(fileName).exists()) {
				@SuppressWarnings("unchecked")
				Map<String, String> tempMap = (Map<String, String>) JsonHelper.toObject(fileName, Map.class);
				for (String key : tempMap.keySet()) {
					String value = tempMap.get(key);
					mappings.put(key, TestState.valueOf(value));
				}
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
