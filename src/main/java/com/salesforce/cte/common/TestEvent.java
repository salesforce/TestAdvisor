/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import java.io.File;
import java.time.Instant;

/**
 * 
 * This class represents the Events emitted from a single test case.
 * 
 * @author gpahuja
 *
 */
public class TestEvent {
	// General event attribute
	private TestEventType eventType;
	private String eventLevel;
	private String eventContent;
	private Instant eventTime;

	// Selenium event attribute
	private String seleniumCmd;
	private String seleniumCmdParam;
	private String seleniumLocator;
	private int screenshotRecordNumber;
	private String screenshotPath;

	public TestEvent(){ /* default constructor for deserialization */ }

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public int getScreenshotRecordNumber() {
		return screenshotRecordNumber;
	}

	public void setScreenshotRecordNumber(int screenshotRecordNumber) {
		this.screenshotRecordNumber = screenshotRecordNumber;
	}

	public String getSeleniumLocator() {
		return seleniumLocator;
	}

	public void setSeleniumLocator(String seleniumLocator) {
		this.seleniumLocator = seleniumLocator;
	}

	public String getSeleniumCmdParam() {
		return seleniumCmdParam;
	}

	public void setSeleniumCmdParam(String seleniumCmdParam) {
		this.seleniumCmdParam = seleniumCmdParam;
	}

	public String getSeleniumCmd() {
		return seleniumCmd;
	}

	public void setSeleniumCmd(String seleniumCmd) {
		this.seleniumCmd = seleniumCmd;
	}

	public Instant getEventTime() {
		return eventTime;
	}

	public void setEventTime(Instant eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}

	public TestEventType getEventType() {
		return eventType;
	}

	public void setEventType(TestEventType eventType) {
		this.eventType = eventType;
	}

	/**
	 * A constructor to set general attributes
	 *
	 * @param eventType    type of the event
	 * @param eventContent represents the content description of the new event
	 *                     object
	 * @param level        event level {@link java.util.logging.Level}
	 */
	public TestEvent(TestEventType eventType, String eventContent, String level) {
		this.setEventType(eventType);
		this.setEventContent(eventContent);
		this.setEventLevel(level);
		this.setEventTime(Instant.now());
	}

	/**
	 * A constructor to set all attributes
	 *
	 * @param eventType       type of the event
	 * @param eventContent    event content
	 * @param level           event level {@link java.util.logging.Level}
	 * @param seleniumCommand this event's Selenium command
	 * @param seleniumParam   this event's Selenium command parameters
	 * @param locator         event locator for WebElement
	 * @param recordNumber    screenshot sequence number
	 * @param screenshot      screenshot file
	 */
	public TestEvent(TestEventType eventType, String eventContent, String level, String seleniumCommand, String seleniumParam, String locator,
			int recordNumber, File screenshot) {
		// do NOT use chained constructor to maintain same stacktrace
		this.setEventType(eventType);
		this.setEventContent(eventContent);
		this.setEventLevel(level);
		this.setEventTime(Instant.now());

		this.setSeleniumCmd(seleniumCommand);
		this.setSeleniumCmdParam(seleniumParam);
		this.setSeleniumLocator(locator);
		this.setScreenshotRecordNumber(recordNumber);
		this.setScreenshotPath(screenshot != null ? screenshot.getAbsolutePath() : "");
	}

}
