/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

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
    
	private String testName;
    private List<TestEvent> eventList = new ArrayList<>();
    private TestStatus testStatus = TestStatus.PASSED;
    private Instant startTime = Instant.now();
    private Instant endTime = Instant.now();
    private String browser = "";
    private String browserVersion = "";
    private String screenResolution = "";
    private boolean isConfiguration = false;
    private long threadId = Thread.currentThread().getId();
    private String traceId = "";

    private List<File> screenShotFileList = new ArrayList<>();
    private Random rand = new Random();

    public List<TestEvent> getEventList(){
        return eventList;
    }

    public void setEventList(List<TestEvent> list){
        eventList = list;
    }
    
    public boolean isConfiguration() {
        return isConfiguration;
    }

    public void setConfiguration(boolean isConfiguration) {
        this.isConfiguration = isConfiguration;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public TestStatus getTestStatus() {
    	return testStatus;
    }

    public void setTestStatus(TestStatus status) {
    	testStatus = status;
    }
    
    public String getTraceId(){
        return traceId;
    }
    
    public long getThreadId(){
        return threadId;
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
    	setEndTime(Instant.now());
    }

    public List<File> getScreenShotFileList(){
        return this.screenShotFileList;
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