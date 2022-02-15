/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public Instant startTime;
    
    @JsonProperty
    public Instant endTime;

    @JsonProperty 
    public String browser;

    @JsonProperty 
    public String browserVersion;

    @JsonProperty 
    public String screenResolution;

    @JsonProperty 
    public boolean isConfiguration;

    @JsonProperty 
    public long threadId;

    private List<File> screenShotFileList = new ArrayList<>();

    @JsonProperty
    private String traceId="";

    private Random rand;
    /**
     * A default constructor for the TestCaseExecution class
     */
    public TestCaseExecution (String testName) {
        this.eventList = new ArrayList<>();
        this.testStatus = TestStatus.PASSED;
        this.startTime = Instant.now();
        this.rand = new Random();
        this.threadId = Thread.currentThread().getId();
        this.testName = testName;
    }

    public void setIsConfiguration(boolean isConfiguration){
        this.isConfiguration = isConfiguration;
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
    	endTime = Instant.now();
    }

    public List<File> getScreenShotFileList(){
        return this.screenShotFileList;
    }

    public String getTraceId(){
        return traceId;
    }
    
    public long getThreadId(){
        return threadId;
    }

    public Instant getStartTime(){
        return startTime;
    }

    /**
     * If trace id is emtpy, generate a random 16 character trace id 
     * @return trace id
     */
    public String generateTraceId() {
        if (!traceId.isEmpty()) return traceId;
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 16) {
            sb.append(Integer.toHexString(rand.nextInt()));
        }
        traceId = sb.substring(0, 16);
        return traceId;
    }
}