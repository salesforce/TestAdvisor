/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import static org.junit.Assert.*;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

import org.junit.Test;

/**
 * 
 * EventTest will test all of the methods in the Event class
 * to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class TestEventTest {

	public TestEvent defaultEvent = new TestEvent(TestEventType.AUTOMATION,"",Level.INFO.toString());
	public TestEvent argsEvent = new TestEvent(TestEventType.URL,"Event X", Level.INFO.toString(),"Clicked on Space Bar","","//locator",5, new File(".","test"));
	
	/**
	 * Tests to make sure that the default constructor for the Event class works as expected
	 */
	@Test
	public void testEventGeneralAttributes() {
		assertEquals(TestEventType.AUTOMATION, defaultEvent.getEventType());
		assertEquals("", defaultEvent.getEventContent());
		assertEquals(Level.INFO.toString(), defaultEvent.getEventLevel());
		assertTrue(Duration.between(defaultEvent.getEventTime(),Instant.now()).toMillis()<1000);
	}

	/**
	 * Tests to make sure that the two argument constructor for the Event class works as expected
	 */
	@Test
	public void testEventAllAttributes() {
		assertEquals(TestEventType.URL, argsEvent.getEventType());
		assertEquals("Event X", argsEvent.getEventContent());
		assertEquals(Level.INFO.toString(), argsEvent.getEventLevel());
		assertTrue(Duration.between(defaultEvent.getEventTime(),Instant.now()).toMillis()<1000);
		
		assertEquals("Clicked on Space Bar", argsEvent.getSeleniumCmd());
		assertEquals("", argsEvent.getSeleniumCmdParam());
		assertEquals("//locator", argsEvent.getSeleniumLocator());
		assertEquals(5, argsEvent.getScreenshotRecordNumber());
		assertTrue(argsEvent.getScreenshotPath().contains("./test"));
	}
}
