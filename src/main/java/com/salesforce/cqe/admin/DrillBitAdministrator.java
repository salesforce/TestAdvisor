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

	private Path registryRoot;
	
    private JsonReporter jsonReporter;

    private Config config = new Config();

    @JsonProperty
    public List<TestCaseExecution> payloadList;

    private static DrillBitAdministrator drillBitAdminInstance = null;
    
    /**
     * A default constructor for the DrillBitAdministrator class
     */
    private DrillBitAdministrator() {
        registryRoot = System.getenv("DRILLBIT_REGISTRY") != null ?
                            Paths.get(System.getenv("DRILLBIT_REGISTRY"))
                            :Paths.get(System.getProperty("user.dir"),retrieveRootDirectory());   

        Path testRun = createTestRun(registryRoot);
        jsonReporter = new JsonReporter(testRun);
        payloadList = new ArrayList<>();
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
    public String retrieveRootDirectory() {
    	/*
    	 * Order of Precedence
    	 * 1) The environment variable's value has been set --> it can be entered via the CLI or read from a property's file
    	 * 2) If the value for the environment variable hasn't been set, you use a default value (utilizes the current function)
    	 */
        String operatingSystem = config.getOS();
        String rootDirectory;

        if (operatingSystem.toLowerCase().contains("mac") || operatingSystem.toLowerCase().contains("linux")) {
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
        return Paths.get("TestRun-" + formattedDate).toString();
    }
    
    /**
     * Creates a hierarchy of empty folders within the Registry for each test run.
     * 
     * @return root_file represents the TestRun-yyyyMMdd-HHmmss folder that will contain the Screenshots sub-directory
     */
    private Path createTestRun(Path root) {
        Path testRunPath = root.resolve(retrieveTestRunDirectory());
        testRunPath.resolve("Screenshots").toFile().mkdirs();
        
        return testRunPath;
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
    
// TODO: This function appends the current instance of the TestCaseExecution class to the JSON file
//    public void saveTestCaseExecution() {
//	 	// Instantiate a JSON Reporter here
//    	// Use the JSONReporter to write to JSON here
//    	// Call on the saveToRegistry(TestCaseExecution testCaseExecution) function
//    }
    
    /**
     * Saves payloadList to the DrillBit Registry in JSON format
     * by calling on the JsonReporter's saveToRegistry() function
     * @return
     * File object of saved test excution list.
     */
    public File saveTestCaseExecutionList() {
    	return jsonReporter.saveToRegistry(payloadList);
    }
    
}
