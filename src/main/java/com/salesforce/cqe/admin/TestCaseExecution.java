package com.salesforce.cqe.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class consolidates all of the events from a single test.
 * An instance of this class will be created at the start of a test and all of the events
 * emanating/resulting from that test will be collected for that specific test
 * and then flushed to the registry once the test has finished running.
 * 
 * @author gpahuja
 *
 */
public class TestCaseExecution {

	@JsonProperty
	public String testName;
	
    @JsonProperty
    public List<TestEvent> eventList;
    
    @JsonProperty
    public TestStatus testStatus;
    
    @JsonProperty
    public String startTime;
    
    @JsonProperty
    public String endTime;

    @JsonIgnore
    private boolean isBeforeMethod = false;

    /**
     * A default constructor for the TestCaseExecution class
     */
    public TestCaseExecution () {
        eventList = new ArrayList<TestEvent>();
        testStatus = TestStatus.PASSED;
        startTime = Instant.now().toString();
    }

    /**
     * This function will return the current test case's name
     * 
     * @return testName represents the current test case's name
     */
    public String getTestName() {
    	return testName;
    }
    
    /**
     * This function will update the current test case's name to the value of 'testName'
     * 
     * @param testName represents the new test name for the current test case
     */
    public void setTestName(String testName) {
    	this.testName = testName;
    }

    /**
     * This function will return the current test case's status
     * 
     * @return testStatus represents the current test case's status
     */
    public TestStatus getTestStatus() {
    	return testStatus;
    }
    
    /**
     * This function will update the current test case's status to the value of 'status'
     * 
     * @param status represents the new test status for the current test case
     */
    public void setTestStatus(TestStatus status) {
    	testStatus = status;
    }
    
    /**
     * This function will append an Event instance 'event' to the 'eventList'
     * and also return 'event'
     *
     * @param event represents an event that was captured by the Event Listener
     * @return event represents the current event being read in from the Event Listener
     */
    public TestEvent appendEvent(TestEvent event) {
        eventList.add(event);

        return event;
    }

	/**
	 * Saves the current test case's end time of execution
	 */
    public void saveEndTime() {
    	endTime = Instant.now().toString();
    }
    
    /**
     * Checks if the current test case is a "Before" method
     * 
     * @return true if the current test case contains a "Before" annotation,
     * otherwise returns false
     */
    @JsonIgnore
    public boolean isBeforeMethod(){
        return this.isBeforeMethod;
    }

    /**
     * Sets the current test case as a "Before" method
     */
    public void setBeforeMethod(){
        this.isBeforeMethod = true;
    }

    /**
     * Un-sets the current test case as a "Before" method
     */
    public void unsetBeforeMethod(){
        this.isBeforeMethod = false;
    }
}