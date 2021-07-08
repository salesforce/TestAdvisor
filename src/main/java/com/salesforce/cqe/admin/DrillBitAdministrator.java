package com.salesforce.cqe.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * This class acts as the manager and will gather information from both the
 * Event Listeners (being dispatched from the Event Dispatcher + RemoteWebDriver) and the test being executed from the
 * Customer Test Project
 * 
 * @author gpahuja
 *
 */
public class DrillBitAdministrator {

	public String prefix;
	
    private JsonReporter jsonReporter;

    @JsonProperty
    public List<TestCaseExecution> payloadList;

    private static DrillBitAdministrator drillBitAdminInstance = null;
    
    /**
     * A default constructor for the DrillBitAdministrator class
     */
    private DrillBitAdministrator() {
    	prefix = System.getenv("DRILLBIT_REGISTRY");
    	Paths.get(prefix, createTestRun().toString()).toFile().mkdirs();
        jsonReporter = new JsonReporter(Paths.get(prefix, createTestRun().toString()).toString());
        payloadList = new ArrayList<TestCaseExecution>();
    }

    /**
     * This function creates a new instance of DrillBitAdministrator
     * if it has not yet been created, otherwise it returns an existing instance.
     * 
     * @return drillBitAdminInstance represents an instance of DrillBitAdministrator
     */
    public static synchronized DrillBitAdministrator getInstance() {
    	
    	if (drillBitAdminInstance == null) {
    		synchronized (DrillBitAdministrator.class) {
    			if (drillBitAdminInstance == null) {
    				drillBitAdminInstance = new DrillBitAdministrator();
    			}
    		}
    	} 
    	
    	return drillBitAdminInstance;
    }
    
	/**
	 * Returns the root directory of the registry based off of the user's OS (compatible with Mac OS, Windows, and Linux)
	 * 
	 * @return a String object containing the root directory of the registry
	 */
    public static String retrieveRootDirectory() {
    	/*
    	 * Order of Precedence
    	 * 1) The environment variable's value has been set --> it can be entered via the CLI or read from a property's file
    	 * 2) If the value for the environment variable hasn't been set, you use a default value (utilizes the current function)
    	 */
        String operating_system = System.getProperty("os.name");
        String rootDirectory;

        if (operating_system.toLowerCase().contains("mac") || operating_system.toLowerCase().contains("linux")) {
        	rootDirectory = ".drillbit";
        }
        else { // Windows
        	rootDirectory = "drillbit";
        }

        return rootDirectory;
    }
    
    /**
     * Returns the absolute path to the specific Test Run folder based off of the current time
     * 
     * @return a String object containing the root directory of the Test Run folder
     */
    public String retrieveTestRunDirectory() {
    	LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
        Path rootPath = Paths.get("TestRun-" + formattedDate);
        String rootDirectory = rootPath.toString();

        return rootDirectory;
    }
    
    /**
     * Creates a hierarchy of empty folders within the Registry for each test run.
     * 
     * @return root_file represents the TestRun-yyyyMMdd-HHmmss folder that will contain the Screenshots sub-directory
     */
    private File createTestRun() {
        String rootDirectory = Paths.get(retrieveRootDirectory(), retrieveTestRunDirectory()).toString();
        File root_file = new File(rootDirectory);
        File screenshots_file = new File(rootDirectory + "/Screenshots");
        if (!screenshots_file.exists()) {
            screenshots_file.mkdirs();
        }
        return root_file;
    }
    
    /**
     * Creates an instance of the TestCaseExecution class and appends it to payloadList, and then returns the instance.
     * 
     * @return testCaseExecution represents an instance of the TestCaseExecution class that represents the current test case
     */
    public TestCaseExecution createTestCaseExecution() {
    	TestCaseExecution testCaseExecution = new TestCaseExecution();
    	
    	payloadList.add(testCaseExecution);
    	
    	return testCaseExecution;
    }
    
    /**
     * Returns the current TestCaseExecution instance which represents the current test case
     * 
     * @return 
     * currentTest represents an instance of the TestCaseExecution class that represents the current test case 
     * null if no test case exists
     */
    public TestCaseExecution getTestCaseExecution() {
    	
    	// this works for v1 - sequential execution
    	// however, how can we modify this to work for v2 - parallel/concurrent execution
    	return payloadList.isEmpty() ? null : payloadList.get(payloadList.size() - 1);
    }
    
//    /**
//     * Appends the current instance of the TestCaseExecution class to the JSON file
//     */
//    public void saveTestCaseExecution() {
//	 	// Instantiate a JSON Reporter here
//    	// Use the JSONReporter to write to JSON here
//    	// Call on the saveToRegistry(TestCaseExecution testCaseExecution) function
//    }
    
    /**
     * Saves payloadList to the DrillBit Registry in JSON format
     * by calling on the JsonReporter's saveToRegistry() function
     */
    public File saveTestCaseExecutionList() {
    	File outputFile = jsonReporter.saveToRegistry(payloadList);
    	
    	return outputFile;
    }
    
}
