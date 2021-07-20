package com.salesforce.cqe.admin;

import java.time.Instant;
import java.util.logging.Level;

/**
 * 
 * This class represents the Events emitted from a single test case.
 * 
 * @author gpahuja
 *
 */
public class TestEvent {
	
	public String eventName;
	public Level eventLevel;
	public String eventContent;
	public String startTime;
	public String endTime;
	
    /**
     * A default constructor for the Event class
     */
	public TestEvent() {
		eventName = "";
		eventContent = "";
		startTime = Instant.now().toString();
	}
	
	/**
	 * A constructor for the Event class that takes in 2 arguments
	 * 
	 * @param eventContent represents the content of the event
	 * @param level represents the priority level of the event
	 */
	public TestEvent(String eventContent, Level level) {
		this( Thread.currentThread().getStackTrace()[2].getClassName() 
				+ "." + Thread.currentThread().getStackTrace()[2].getMethodName() 
				,eventContent,level);
	}

	/**
	 * A constructor for the Event class that takes in two arguments
	 * 
	 * @param eventName 
	 * represents the name of the new Event object
	 * @param eventContent 
	 * represents the content description of the new Event object
	 * @param level
	 * event level, {@link java.util.logging.Level}
	 */
	public TestEvent(String eventName, String eventContent, Level level) {
		this.eventName = eventName;
		this.eventContent = eventContent;
		this.eventLevel = level;
		startTime = Instant.now().toString();
	}
	
	/**
	 * Returns the event's name
	 * 
	 * @return eventName represents the name of the current event
	 */
	public String getEventName() {
		return eventName;
	}
	
	/**
	 * Updates the event's name
	 * 
	 * @param eventName represents the updated name of the event
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	/**
	 * Returns the event's content description
	 * 
	 * @return eventContent represents the content description of the current event
	 */
	public String getEventContent() {
		return eventContent;
	}
	
	/**
	 * Updates the event's content description
	 * 
	 * @param eventContent represents the updated content description of the event
	 */
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	
	/**
	 * Saves the event's end time
	 */
    public void saveEndTime() {
    	endTime = Instant.now().toString();
    }
    
}
