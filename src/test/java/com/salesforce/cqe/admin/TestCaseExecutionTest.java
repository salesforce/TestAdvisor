package com.salesforce.cqe.admin;

import com.salesforce.cqe.driver.listener.Event;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gpahuja
 *
 */
public class TestCaseExecutionTest {

    private TestCaseExecution testCaseExecution = new TestCaseExecution();
    private Event event = new Event();
    
    /**
     * Tests to make sure that the appendEvent() method works as expected
     */
    @Test
    public void testAppendEvent() {
        System.out.println("Running the test(s) for appendEvent()");

        assertEquals(0, testCaseExecution.eventList.size());

        testCaseExecution.appendEvent(event);

        assertEquals(1, testCaseExecution.eventList.size());

        for (int x = 0; x < 9; x ++) {
            testCaseExecution.appendEvent(event);
        }

        assertEquals(10, testCaseExecution.eventList.size());
    }

//    /**
//     * Tests to make sure that the saveTestCaseExecution() method works as expected
//     */
//    @Test
//    public void testSaveTestCaseExecution() {
//        System.out.println("Running the test(s) for saveTestCaseExecution()");
//        fail("Not yet implemented");
//    }
}