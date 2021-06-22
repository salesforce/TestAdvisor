package com.salesforce.cqe.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * This class will assist in converting the information extracted from the test suite's payloads
 * into a properly formatted JSON file following a specific JSON Schema
 *
 */
public class JsonReporter {

	
	private ObjectMapper objectMapper;
	private Map<String, Object> map;
	
	public JsonReporter() {
		objectMapper = new ObjectMapper();
		map = new HashMap<>();
	}
	

	/**
	 * Returns the root directory of the registry based off of the user's OS (compatible with Mac OS, Windows, and Linux)
	 * 
	 * @return a String object containing the root directory of the registry
	 */
    static String retrieveRootDirectory() {
    	/*
    	 * Order of Precedence
    	 * 1) The environment variable's value has been set --> it can be entered via the CLI or read from a property's file
    	 * 2) If the value for the environment variable hasn't been set, you use a default value (utilizes the current function)
    	 */
        String operating_system = System.getProperty("os.name");
        String rootDirectory;

        if (operating_system.contains("Mac") || operating_system.contains("Linux")) {
        	rootDirectory = ".drillbit";
        }
        else { // Windows
        	rootDirectory = "drillbit";
        }
        // You can use '/' regardless of the OS
        
        return rootDirectory;
    }
    
    /**
     * Returns the absolute path to the specific Test Run folder based off of the current time
     * 
     * @return a String object containing the root directory of the Test Run folder
     */
    static String retrieveAbsoluteRootDirectory() {
    	LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
        Path rootPath = Paths.get(JsonReporter.retrieveRootDirectory(), "TestRun-", formattedDate);
        String rootDirectory = rootPath.toString();

        return rootDirectory;
    }
    
	public void saveToRegistry(List<TestCaseExecution> payloadList) {
		try {
			String outputFilePath = Paths.get(retrieveAbsoluteRootDirectory(), "test-result.json").toString();
			objectMapper.writeValue(Paths.get(outputFilePath).toFile(), payloadList);
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
	}

}