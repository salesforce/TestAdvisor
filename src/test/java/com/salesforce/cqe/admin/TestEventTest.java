package com.salesforce.cqe.admin;

import static org.junit.Assert.*;

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

	public TestEvent defaultEvent = new TestEvent();
	public TestEvent argsEvent = new TestEvent("Event X", "Clicked on Space Bar",Level.INFO.toString());
	
	/**
	 * Tests to make sure that the default constructor for the Event class works as expected
	 */
	@Test
	public void testEvent() {
		assertEquals("", defaultEvent.getEventName());
		assertEquals("", defaultEvent.getEventContent());
	}

	/**
	 * Tests to make sure that the two argument constructor for the Event class works as expected
	 */
	@Test
	public void testEventStringString() {
		assertEquals("Event X", argsEvent.getEventName());
		assertEquals("Clicked on Space Bar", argsEvent.getEventContent());
	}

    /**
     * Tests to make sure that the getEventName() method works as expected
     */
	@Test
	public void testGetEventName() {
		assertEquals("", defaultEvent.getEventName());
		assertEquals("Event X", argsEvent.getEventName());
	}

    /**
     * Tests to make sure that the setEventName() method works as expected
     */
	@Test
	public void testSetEventName() {
		assertEquals("", defaultEvent.getEventName());
		assertEquals("Event X", argsEvent.getEventName());
		
		defaultEvent.setEventName("Event A");
		argsEvent.setEventName("Event B");
		
		assertEquals("Event A", defaultEvent.getEventName());
		assertEquals("Event B", argsEvent.getEventName());
	}

    /**
     * Tests to make sure that the getEventContent() method works as expected
     */
	@Test
	public void testGetEventContent() {
		assertEquals("", defaultEvent.getEventContent());
		assertEquals("Clicked on Space Bar", argsEvent.getEventContent());
	}

    /**
     * Tests to make sure that the setEventContent() method works as expected
     */
	@Test
	public void testSetEventContent() {
		assertEquals("", defaultEvent.getEventContent());
		assertEquals("Clicked on Space Bar", argsEvent.getEventContent());
		
		defaultEvent.setEventContent("Clicked on 'A' key");
		argsEvent.setEventContent("Clicked on 'B' key");
		
		assertEquals("Clicked on 'A' key", defaultEvent.getEventContent());
		assertEquals("Clicked on 'B' key", argsEvent.getEventContent());
	}

    /**
     * Tests to make sure that the saveEndTime() method works as expected
     */
	@Test
	public void testSaveEndTime() {
		String expectedTime = Instant.now().toString();
		defaultEvent.saveEndTime();
		
		assertEquals(expectedTime.substring(0, 23), defaultEvent.endTime.substring(0, 23));
	}

	@Test
	public void testEventDefaultName() {
		TestEvent event = new TestEvent("",Level.INFO.toString());
		assertEquals("com.salesforce.cqe.admin.EventTest.testEventDefaultName", event.getEventName());
	}

}
