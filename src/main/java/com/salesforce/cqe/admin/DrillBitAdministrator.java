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
 */
public class DrillBitAdministrator { // Look at implementing the Singleton Pattern for this class

    private JsonReporter jsonReporter;

    @JsonProperty
    public List<TestCaseExecution> payloadList;

    private static DrillBitAdministrator drillBitAdminInstance = null; 
    
    /**
     * A default constructor for the DrillBitAdministrator class
     */
    private DrillBitAdministrator() {
        jsonReporter = new JsonReporter();
        payloadList = new ArrayList<TestCaseExecution>();
        
        createTestRun();
    }

    /**
     * 
     * @return
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
     * Creates a hierarchy of empty folders within the Registry for each test run.
     * 
     * @return root_file represents the TestRun-yyyyMMdd-HHmmss folder that will contain the Screenshots sub-directory
     */
    private File createTestRun() {
        String rootDirectory = JsonReporter.retrieveAbsoluteRootDirectory();
        
        File root_file = new File(rootDirectory);
        File screenshots_file = new File(rootDirectory + "/Screenshots");
        System.out.println("Root Directory: " + rootDirectory);
        System.out.println("Screenshots Subdirectory: " + rootDirectory + "/Screenshots");
        if (!screenshots_file.exists()) {
            screenshots_file.mkdirs();
            System.out.println("Root Directory + Screenshots Subdirectory successfully created!");
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
     * @return currentTest represents an instance of the TestCaseExecution class that represents the current test case 
     */
    public TestCaseExecution getTestCaseExecution() {
    	
    	// this works for v1 - sequential execution
    	// however, how can we modify this to work for v2 - parallel/concurrent execution
    	TestCaseExecution currentTest = payloadList.get(payloadList.size() - 1);
    	
    	return currentTest;
    }
    
    /**
     * 
     */
    public void saveTestCaseExecutionList() {
    	// saves payloadList to the registry in JSON format
    	// by calling on the JsonReporter's saveToRegistry() function
    	
    	jsonReporter.saveToRegistry(payloadList);
    }

}
