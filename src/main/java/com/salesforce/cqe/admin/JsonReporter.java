package com.salesforce.cqe.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
		objectMapper = new ObjectMapper();
		objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        testRunRoot = path;
	}
	
    
    /**
     * Saves the list of TestCaseExecution objects to a single JSON file
     * 
     * @param testResult represents test result object
     * @return a File object representing the JSON file containing the list of TestCaseExecution objects
     */
	public File saveToRegistry(DrillbitTestResult testResult) {
			Path outputFilePath = testRunRoot.resolve("test-result.json");
			// Decide whether the program should stop if it hits an error or continue running
			try {
				objectWriter.withDefaultPrettyPrinter().writeValue(outputFilePath.toFile(), testResult);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return outputFilePath.toFile();
	}

}