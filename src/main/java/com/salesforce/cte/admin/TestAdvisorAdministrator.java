package com.salesforce.cte.admin;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.salesforce.cte.common.TestAdvisorResult;
import com.salesforce.cte.common.TestCaseExecution;

import java.io.File;
import java.io.IOException;
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
public class TestAdvisorAdministrator {

    private TestAdvisorResult testResult = new TestAdvisorResult();
	private Path registryRoot;
    private JsonReporter jsonReporter;
    private Config config = new Config();
    private static TestAdvisorAdministrator taAdminInstance = null;
    
    /**
     * A default constructor for the TestAdvisorAdministrator class
     */
    private TestAdvisorAdministrator() {
        registryRoot = System.getenv("TEST_ADVISOR_REGISTRY") != null ?
                            Paths.get(System.getenv("TEST_ADVISOR_REGISTRY"))
                            :Paths.get(System.getProperty("user.dir"),retrieveRootDirectory());   

        Path testRun = createTestRun(registryRoot);
        jsonReporter = new JsonReporter(testRun);
    }

    /**
     * Set a configuration
     * @param config
     * new config instance
     */
    public void setConfig(Config config){
        this.config = config;
    }

    /**
     * This function creates a new instance of TestAdvisorAdministrator
     * if it has not yet been created, otherwise it returns an existing instance.
     * 
     * @return taAdminInstance represents an instance of TestAdvisorAdministrator
     */
    public static synchronized TestAdvisorAdministrator getInstance() {
    	
    	if (taAdminInstance == null) {
    		synchronized (TestAdvisorAdministrator.class) {
    			if (taAdminInstance == null) {
    				taAdminInstance = new TestAdvisorAdministrator();
    			}
    		}
    	} 
    	return taAdminInstance;
    }
    
    /**
     * Get current test result object
     * @return current test result
     */
    public TestAdvisorResult getTestResult(){
        return this.testResult;
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
        	rootDirectory = ".testadvisor";
        }
        else { // Windows
        	rootDirectory = "testadvisor";
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
    	
    	testResult.testCaseExecutionList.add(testCaseExecution);
    	
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
    	return testResult.testCaseExecutionList.isEmpty() ? null : testResult.testCaseExecutionList.get(testResult.testCaseExecutionList.size() - 1);
    }

    /**
     * Start a test run, save start time
     */
    public void startTestRun(){
        this.testResult.buildStartTime = Instant.now();
        this.testResult.version = TestAdvisorAdministrator.class.getClass().getPackage().getImplementationVersion();
    }

    /**
     * End a test run, save end time
     */
    public void endTestRun(){
        this.testResult.buildEndTime = Instant.now();
    }
    
    /**
     * Saves test result to the TestAdvisor Registry in JSON format
     * by calling on the JsonReporter's saveToRegistry() function
     * @return
     * File object of saved test result.
     * @throws IOException
     * throws IOException when fail to write result file
     */
    public File saveTestResult() throws IOException {
    	return jsonReporter.saveToRegistry(testResult);
    }
    
}
