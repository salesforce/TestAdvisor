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
	public String eventSource;
	public String eventLevel;
	public String eventContent;
	public Instant eventTime;

	// Selenium event attribute
	public String seleniumCmd;
	public String seleniumCmdParam;
	public String seleniumLocator;
	public int screenshotRecordNumber;
	public String screenshotPath;

	/**
	 * Default constructor for de-serialization
	 */
	public TestEvent() {
	}

	/**
	 * A constructor to set general attributes
	 * 
	 * @param eventContent represents the content description of the new event
	 *                     object
	 * @param level        event level {@link java.util.logging.Level}
	 */
	public TestEvent(String eventContent, String level) {
		this.eventSource = Thread.currentThread().getStackTrace()[2].getClassName();
		this.eventContent = eventContent;
		this.eventLevel = level;
		this.eventTime = Instant.now();
	}

	/**
	 * A constructor to set all attributes
	 * 
	 * @param eventContent    event content
	 * @param level           event level {@link java.util.logging.Level}
	 * @param seleniumCommand this event's Selenium command
	 * @param seleniumParam   this event's Selenium command parameters
	 * @param locator         event locator for WebElement
	 * @param recordNumber    screenshot sequence number
	 * @param screenshot      screenshot file
	 */
	public TestEvent(String eventContent, String level, String seleniumCommand, String seleniumParam, String locator,
			int recordNumber, File screenshot) {
		// do NOT use chained constructor to maintain same stacktrace
		this.eventSource = Thread.currentThread().getStackTrace()[2].getClassName();
		this.eventContent = eventContent;
		this.eventLevel = level;
		this.eventTime = Instant.now();

		this.seleniumCmd = seleniumCommand;
		this.seleniumCmdParam = seleniumParam;
		this.seleniumLocator = locator;
		this.screenshotRecordNumber = recordNumber;
		this.screenshotPath = screenshot != null ? screenshot.getAbsolutePath() : "";
	}

	/**
	 * Returns the event's source
	 * 
	 * @return eventSource represents who created current event
	 */
	public String getEventSource() {
		return eventSource;
	}

	/**
	 * Returns the event's content description
	 * 
	 * @return eventContent represents the content description of the current event
	 */
	public String getEventContent() {
		return eventContent;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setStreenshotPath(String path) {
		this.screenshotPath = path;
	}

	public int getScreenshotRecordNumber() {
		return screenshotRecordNumber;
	}

	public String getSeleniumLocator() {
		return seleniumLocator;
	}

	public String getSeleniumCmdParam() {
		return seleniumCmdParam;
	}

	public String getSeleniumCmd() {
		return seleniumCmd;
	}

	public Instant getEventTime() {
		return eventTime;
	}

	public String getEventLevel() {
		return eventLevel;
	}
}
