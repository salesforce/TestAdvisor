package com.salesforce.cqe.admin;

import java.time.Instant;

/**
 * 
 * This class represents the Events emitted from a single test case.
 * 
 * @author gpahuja
 *
 */
public class Event {
	
	public String eventName;
	public String eventContent;
	public String startTime;
	public String endTime;
	
    /**
     * A default constructor for the Event class
     */
	public Event() {
		eventName = "";
		eventContent = "";
		startTime = Instant.now().toString();
	}
	
	/**
	 * A constructor for the Event class that takes in two arguments
	 * 
	 * @param eventName represents the name of the new Event object
	 * @param eventContent represents the content description of the new Event object
	 */
	public Event(String eventName, String eventContent) {
		this.eventName = eventName;
		this.eventContent = eventContent;
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
