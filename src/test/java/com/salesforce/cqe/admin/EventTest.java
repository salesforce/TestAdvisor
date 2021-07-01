package com.salesforce.cqe.admin;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

/**
 * 
 * EventTest will test all of the methods in the Event class
 * to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class EventTest {

	private Event defaultEvent = new Event();
	private Event argsEvent = new Event("Event X", "Clicked on Space Bar");
	
	/**
	 * Tests to make sure that the default constructor for the Event class works as expected
	 */
	@Test
	public void testEvent() {
        System.out.println("Running the test(s) for the default constructor for the Event class");
		assertEquals("", defaultEvent.getEventName());
		assertEquals("", defaultEvent.getEventContent());
	}

	/**
	 * Tests to make sure that the two argument constructor for the Event class works as expected
	 */
	@Test
	public void testEventStringString() {
        System.out.println("Running the test(s) for the two argument constructor for the Event class");
		assertEquals("Event X", argsEvent.getEventName());
		assertEquals("Clicked on Space Bar", argsEvent.getEventContent());
	}

    /**
     * Tests to make sure that the getEventName() method works as expected
     */
	@Test
	public void testGetEventName() {
        System.out.println("Running the test(s) for getEventName()");
		assertEquals("", defaultEvent.getEventName());
		assertEquals("Event X", argsEvent.getEventName());
	}

    /**
     * Tests to make sure that the setEventName() method works as expected
     */
	@Test
	public void testSetEventName() {
		System.out.println("Running the test(s) for setEventName()");
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
		System.out.println("Running the test(s) for getEventContent()");
		assertEquals("", defaultEvent.getEventContent());
		assertEquals("Clicked on Space Bar", argsEvent.getEventContent());
	}

    /**
     * Tests to make sure that the setEventContent() method works as expected
     */
	@Test
	public void testSetEventContent() {
		System.out.println("Running the test(s) for setEventContent()");
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
		System.out.println("Running the test(s) for saveEndTime()");
		String expectedTime = Instant.now().toString();
		defaultEvent.saveEndTime();
		
		assertEquals(expectedTime.substring(0, 23), defaultEvent.endTime.substring(0, 23));
	}

}
