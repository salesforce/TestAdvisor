/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.salesforce.cte.common.TestAdvisorResult;
import com.salesforce.cte.common.TestCaseExecution;
import com.salesforce.cte.common.TestEvent;

/**
 * 
 * This class will assist in converting the information extracted from 
 * the test suite's payloads into a properly formatted JSON file
 * following a specific JSON Schema
 * 
 * @author gpahuja
 *
 */
public class JsonReporter {

	
	private ObjectMapper objectMapper;
	private ObjectWriter objectWriter;
	private Path testRunRoot;
	
	/**
	 * A constructor for the JsonReporter class that takes in one argument
	 * 
	 * @param path represents the path of the folder that will contain the resulting JSON file
	 */
	public JsonReporter(Path path) {
		objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
							.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        testRunRoot = path;
	}
	
    
    /**
     * Saves the list of TestCaseExecution objects to a single JSON file
     * 
     * @param testResult represents test result object
     * @return a File object representing the JSON file containing the list of TestCaseExecution objects
     * @throws IOException throws IOException when fail to write result file
	 * 
     */
	public File saveToRegistry(TestAdvisorResult testResult) throws IOException {
			//process screenshot files
			Path screenshotPath = testRunRoot.resolve("Screenshots");
			if (!screenshotPath.toFile().exists())
				screenshotPath.toFile().mkdirs();
			for (TestCaseExecution test : testResult.getTestCaseExecutionList()) {
				for(TestEvent event : test.getEventList()){
					if (event.getScreenshotPath() == null || event.getScreenshotPath().trim().isEmpty()) continue;
					String newName = String.format("%05d.png", event.getScreenshotRecordNumber());
					Files.move(Paths.get(event.getScreenshotPath()), screenshotPath.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
					event.setScreenshotPath(screenshotPath.resolve(newName).toString());
				}
			}

			//save json file
			Path outputFilePath = testRunRoot.resolve("test-result.json");
			// Decide whether the program should stop if it hits an error or continue running
	
			objectWriter.withDefaultPrettyPrinter().writeValue(outputFilePath.toFile(), testResult);
			
			return outputFilePath.toFile();
	}

}