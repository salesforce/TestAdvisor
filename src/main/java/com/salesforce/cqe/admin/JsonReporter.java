package com.salesforce.cqe.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
     * @param payloadList represents the list of TestCaseExecution objects
     * @return a File object representing the JSON file containing the list of TestCaseExecution objects
     */
	public File saveToRegistry(DrillbitTestResult testResult) {
			String outputFilePath = Paths.get(testRunRoot.toString(), "test-result.json").toString();
			// Decide whether the program should stop if it hits an error or continue running
			try {
				objectWriter.withDefaultPrettyPrinter().writeValue(Paths.get(outputFilePath).toFile(), testResult);
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

			return Paths.get(outputFilePath).toFile();
	}

}