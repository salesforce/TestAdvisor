package com.salesforce.cte.admin;

import static org.junit.Assert.*;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

import com.salesforce.cte.common.TestEvent;

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

	public TestEvent defaultEvent = new TestEvent("",Level.INFO.toString());
	public TestEvent argsEvent = new TestEvent("Event X", Level.INFO.toString(),"Clicked on Space Bar","","//locator",5, new File(".","test"));
	
	/**
	 * Tests to make sure that the default constructor for the Event class works as expected
	 */
	@Test
	public void testEventGeneralAttributes() {
		assertEquals("com.salesforce.cte.admin.TestEventTest", defaultEvent.getEventSource());
		assertEquals("", defaultEvent.getEventContent());
		assertEquals(Level.INFO.toString(), defaultEvent.getEventLevel());
		assertTrue(Duration.between(defaultEvent.getEventTime(),Instant.now()).toMillis()<1000);
	}

	/**
	 * Tests to make sure that the two argument constructor for the Event class works as expected
	 */
	@Test
	public void testEventAllAttributes() {
		assertEquals("com.salesforce.cte.admin.TestEventTest", argsEvent.getEventSource());
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