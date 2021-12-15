/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.salesforce.cte.common.TestAdvisorResult;
import com.salesforce.cte.common.TestCaseExecution;
import com.salesforce.cte.common.TestEvent;
import com.salesforce.cte.common.TestStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 
 * JsonReporterTest will test all of the methods in the JsonReporter class
 * to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class JsonReporterTest {

	private JsonReporter jsonReporter;
	private Path root;

	/**
	 * Creates a temporary test run directory before the test starts
	 *
	 * @throws IOException if an I/O error occurs or the temporary-file directory does not exist
	 */
	@Before
	public void createFolderStructure() throws IOException {
		root = Files.createTempDirectory("").resolve(".testadvisor").resolve("TestRun-20210629-135657");
		
		root.toFile().mkdirs();
		
		jsonReporter = new JsonReporter(root);
	}

	/**
	 * Deletes the previously-created temporary test run directory after all of the tests have finished running
	 */
	@After
	public void deleteFolderStructure() {
		root.toFile().deleteOnExit();
	}

    /**
     * Tests to make sure that the saveToRegistry() method works as expected
     * @throws IOException if an I/O error occurs or if the temporary-file directory doesn't exist
     */
	@Test
	public void testSaveToRegistry() throws IOException {
		Config mockConfig = mock(Config.class);
		when(mockConfig.getOS()).thenReturn("Mac OS X");
		
		List<TestCaseExecution> payloadList = new ArrayList<TestCaseExecution>();
		TestCaseExecution testCaseOne = new TestCaseExecution();
		TestCaseExecution testCaseTwo = new TestCaseExecution();
		TestCaseExecution testCaseThree = new TestCaseExecution();
		
		testCaseOne.setTestName("Test 1");
		testCaseTwo.setTestName("Test 2");
		testCaseThree.setTestName("Test 3");
		
		testCaseOne.saveEndTime();
		testCaseTwo.saveEndTime();
		testCaseThree.saveEndTime();
		
		payloadList.add(testCaseOne);
		payloadList.add(testCaseTwo);
		payloadList.add(testCaseThree);

		TestAdvisorResult testResult = new TestAdvisorResult();
		testResult.testCaseExecutionList = payloadList;
		
		Instant now = Instant.now();
    
        testResult.buildStartTime = now;
        testResult.buildEndTime = now.plusSeconds(5);
        testResult.version = TestAdvisorAdministrator.getInstance().version;
        testResult.testCaseExecutionList = new ArrayList<TestCaseExecution>();

        TestCaseExecution testCaseExecution = new TestCaseExecution();
        testCaseExecution.browser = "Chrome";
        testCaseExecution.browserVersion = "90.1";
        testCaseExecution.screenResolution = "1920*1080";
        testCaseExecution.startTime = now;
        testCaseExecution.endTime = now.plusSeconds(5);
        testCaseExecution.testStatus = TestStatus.PASSED;
        testCaseExecution.testName = "TestCase1";
        testCaseExecution.eventList = new ArrayList<>();
        testCaseExecution.eventList.add(new TestEvent("test content", "Info"));
		testCaseExecution.eventList.add(new TestEvent("screentshot", "Info", "click", "null", "locator", 1, File.createTempFile("screenshot","")));
		
		testResult.testCaseExecutionList.add(testCaseExecution);
		File outputFile = jsonReporter.saveToRegistry(testResult);
		assertTrue(root.toFile().exists());
		assertTrue(root.toFile().isDirectory());
		assertTrue(root.resolve("Screenshots").toFile().isDirectory());
		assertTrue(root.resolve("Screenshots").resolve("00001.png").toFile().exists());
		assertTrue(outputFile.getParent().toString().contains(".testadvisor/TestRun-20210629-135657"));
		assertEquals("test-result.json", outputFile.getName());
	}

}