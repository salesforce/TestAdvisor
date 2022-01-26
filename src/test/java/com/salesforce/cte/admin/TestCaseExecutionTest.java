/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

import com.salesforce.cte.common.TestCaseExecution;
import com.salesforce.cte.common.TestEvent;
import com.salesforce.cte.common.TestStatus;

/**
 * 
 * TestCaseExecutionTest will test all of the methods in the
 * TestCaseExecution class to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class TestCaseExecutionTest {

    private TestCaseExecution testCaseExecution = new TestCaseExecution();
    private TestEvent event = new TestEvent("",Level.INFO.toString());
    
	/**
	 * Tests to make sure that the default constructor for the TestCaseExecution class works as expected
	 */
	@Test
	public void testTestCaseExecution() {
		assertEquals(0, testCaseExecution.eventList.size());
		assertEquals(TestStatus.PASSED, testCaseExecution.testStatus);
		
		Instant expectedTime = Instant.now();
		testCaseExecution.saveEndTime();
		
		assertTrue(Duration.between(expectedTime, testCaseExecution.endTime).toMillis()<100);
	}
	
    /**
     * Tests to make sure that the getTestName() method works as expected
     */
	@Test
	public void testGetTestName() {
		assertNull(testCaseExecution.getTestName());
		
		testCaseExecution.setTestName("Test X");
		assertEquals("Test X", testCaseExecution.getTestName());
	}
	
    /**
     * Tests to make sure that the setTestName() method works as expected
     */
	@Test
	public void testSetTestName() {
		testCaseExecution.setTestName("Test A");
		assertEquals("Test A", testCaseExecution.getTestName());
	}
	
    /**
     * Tests to make sure that the getTestStatus() method works as expected
     */
	@Test
	public void testGetTestStatus() {
		assertEquals(TestStatus.PASSED, testCaseExecution.getTestStatus());
		
		testCaseExecution.setTestStatus(TestStatus.FAILED);
		assertEquals(TestStatus.FAILED, testCaseExecution.getTestStatus());
	}
	
    /**
     * Tests to make sure that the setTestStatus() method works as expected
     */
	@Test
	public void testSetTestStatus() {
		testCaseExecution.setTestStatus(TestStatus.SKIPPED);
		assertEquals(TestStatus.SKIPPED, testCaseExecution.getTestStatus());
	}
    
    /**
     * Tests to make sure that the appendEvent() method works as expected
     */
    @Test
    public void testAppendEvent() {
        assertEquals(0, testCaseExecution.eventList.size());

        testCaseExecution.appendEvent(event);

        assertEquals(1, testCaseExecution.eventList.size());

        for (int x = 0; x < 9; x ++) {
            testCaseExecution.appendEvent(event);
        }

        assertEquals(10, testCaseExecution.eventList.size());
    }

	@Test
	public void testTraceId(){
		assertEquals("", testCaseExecution.getTraceId());
		String traceId = testCaseExecution.generateTraceId();
		assertEquals(16, traceId.length());
		assertEquals(traceId, testCaseExecution.getTraceId());
		assertEquals(traceId, testCaseExecution.generateTraceId());

		String traceId2 = new TestCaseExecution().generateTraceId();
		assertNotEquals(traceId, traceId2);
	}

}