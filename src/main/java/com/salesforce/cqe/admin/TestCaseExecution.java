package com.salesforce.cqe.admin;

import com.salesforce.cqe.driver.listener.Event;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.sql.Timestamp;
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
 */
public class TestCaseExecution {

    @JsonProperty
    public List<Event> eventList;
    
    @JsonProperty
    public TestStatus testStatus;
    
    @JsonProperty
    public String startTime;
    
    @JsonProperty
    public String endTime;

    /**
     * A default constructor for the TestCaseExecution class
     */
    public TestCaseExecution () {
        eventList = new ArrayList<Event>();
        testStatus = TestStatus.PASSED;
        startTime = Instant.now().toString();
    }

    /**
     * This function will append an Event instance 'event' to the 'eventList'
     * and also return 'event'
     *
     * @param event represents an event that was captured by the Event Listener
     * @return event represents the current event being read in from the Event Listener
     */
    public Event appendEvent(Event event) {
        eventList.add(event);

        return event;
    }

//    /**
//     * Appends the current instance of the TestCaseExecution class
//     * to the JSON file.
//     */
//    public void saveTestCaseExecution() {
//        // Use the JSONReporter to write to JSON here
//    	endTime = Instant.now().toString();
//    	
//    }
}