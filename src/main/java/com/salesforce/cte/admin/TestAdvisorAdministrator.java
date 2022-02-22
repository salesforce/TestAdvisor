/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
    
    private TestAdvisorResult testResult = new TestAdvisorResult();
    private ConcurrentMap<Long,TestCaseExecution> threadTestCaseMap = new ConcurrentHashMap<>();
	private Path registryRoot;
    private JsonReporter jsonReporter;
    private static TestAdvisorAdministrator taAdminInstance = null;
    
    private static final String VERSION_PROPERTY="testadvisor.lib.version";
    private String version = "";
    public String getVersion(){
        return this.version;
    }

    /**
     * A default constructor for the TestAdvisorAdministrator class
     */
    private TestAdvisorAdministrator() {
        registryRoot = System.getenv("TEST_ADVISOR_REGISTRY") != null ?
                            Paths.get(System.getenv("TEST_ADVISOR_REGISTRY")).normalize()
                            :Paths.get(System.getProperty("user.dir"),retrieveRootDirectory()).normalize();   

        Path testRun = createTestRun(registryRoot);
        LOGGER.log(Level.INFO, "Test Run created: {0}",testRun);
        jsonReporter = new JsonReporter(testRun);
        
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
            version = (properties.getProperty(VERSION_PROPERTY));
        }catch(IOException ex){
            LOGGER.log(Level.SEVERE, ex.toString());
        }
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
        String operatingSystem = TestAdvisorSwitch.getOS();
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
     * Creates an instance of the TestCaseExecution class and appends it to test result
     * update thread test case map and then returns the instance.
     * 
     * @return TestCaseExecution object that represents the current test case
     */
    public synchronized TestCaseExecution createTestCaseExecution(String testName) {
        LOGGER.log(Level.INFO, "create test case execution object {0}",testName);
    	TestCaseExecution testCaseExecution = new TestCaseExecution();
        testCaseExecution.setTestName(testName);
    	testResult.getTestCaseExecutionList().add(testCaseExecution);

        //only track the current test case executioni object for the running thread
        //every thread contains its own test case execution object
    	threadTestCaseMap.put(Thread.currentThread().getId(), testCaseExecution);
    	return testCaseExecution;
    }
    
    /**
     * Returns the current TestCaseExecution instance for current thread
     * which represents the current test case
     * 
     * @return 
     * TestCaseExecution object 
     * null if no test case exists
     */
    public synchronized TestCaseExecution getTestCaseExecution() {
        return threadTestCaseMap.get(Thread.currentThread().getId());
    }

    /**
     * Start a test run, save start time
     */
    public void startTestRun(){
        this.testResult.setBuildStartTime(Instant.now());
        this.testResult.setVersion(TestAdvisorAdministrator.getInstance().version);
    }

    /**
     * End a test run, save end time
     */
    public void endTestRun(){
        this.testResult.setBuildEndTime(Instant.now());
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
