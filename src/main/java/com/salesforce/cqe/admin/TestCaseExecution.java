package com.salesforce.cqe.admin;

import com.salesforce.cqe.driver.listener.Event;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestCaseExecution {

    public static final String REGISTRY_ROOT = "/registry";

    @JsonProperty
    public List<Event> eventList;

    @JsonProperty
    public String testStatus;

    /**
     * A default constructor for the TestCaseExecution class
     */
    public TestCaseExecution () {
        eventList = new ArrayList<Event>();
        testStatus = "Undetermined";
    }

    /**
     * This function will append an Event instance 'event' to the 'eventList'
     * and also return 'event'
     *
     * @param event represents the
     * @return event
     */
    public Event appendEvent(Event event) {
        eventList.add(event);

        return event;
    }

    /**
     * Appends the current instance of the TestCaseExecution class
     * to the JSON file.
     *
     */
    public void saveTestCaseExecution() {
        String filePathToRegistry = "";
        String testRunDirectory = ""; // Can get from create() within DBA class
        File file = new File(filePathToRegistry + "/" + REGISTRY_ROOT + testRunDirectory + "/test-result.json"); // Look at java.nio

        if (file.exists()) {
            // append current TCE instance to the already existing JSON file
        }
        else {
            // create a new JSON file called "test-result.json" and append the TCE instance to the new JSON file
        }
    }
}