package com.salesforce.cqe.admin;

import com.salesforce.cqe.driver.listener.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCaseExecutionTest {

    TestCaseExecution testCaseExecution;
    Event event;

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting up the for the TestCaseExecutionTest class");
        testCaseExecution = new TestCaseExecution();
        event = new Event();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tearing down the TestCaseExecutionTest class");

        testCaseExecution = null;
        event = null;

        assertNull(testCaseExecution);
        assertNull(event);
    }

    @Test
    public void appendEvent() {
        System.out.println("Running the test(s) for appendEvent()");

        assertEquals(0, testCaseExecution.eventList.size());

        testCaseExecution.appendEvent(event);

        assertEquals(1, testCaseExecution.eventList.size());

        for (int x = 0; x < 9; x ++) {
            testCaseExecution.appendEvent(event);
        }

        assertEquals(10, testCaseExecution.eventList.size());
    }

    @Test
    public void saveTestCaseExecution() {
        System.out.println("Running the test(s) for saveTestCaseExecution()");


    }
}