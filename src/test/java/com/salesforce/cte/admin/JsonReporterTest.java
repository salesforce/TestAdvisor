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
import com.salesforce.cte.common.TestEventType;
import com.salesforce.cte.common.TestStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
		System.setProperty("os.name", "Mac OS X");
		List<TestCaseExecution> payloadList = new ArrayList<TestCaseExecution>();
		TestCaseExecution testCaseOne = new TestCaseExecution();
		testCaseOne.setTestName("Test 1");
		TestCaseExecution testCaseTwo = new TestCaseExecution();
		testCaseTwo.setTestName("Test 2");
		TestCaseExecution testCaseThree = new TestCaseExecution();
		testCaseThree.setTestName("Test 3");

		testCaseOne.saveEndTime();
		testCaseTwo.saveEndTime();
		testCaseThree.saveEndTime();
		
		payloadList.add(testCaseOne);
		payloadList.add(testCaseTwo);
		payloadList.add(testCaseThree);

		TestAdvisorResult testResult = new TestAdvisorResult();
		testResult.setTestCaseExecutionList(payloadList);
		
		Instant now = Instant.now();
    
        testResult.setBuildStartTime(now);
        testResult.setBuildEndTime(now.plusSeconds(5));
        testResult.setVersion(TestAdvisorAdministrator.getInstance().getVersion());
        testResult.setTestCaseExecutionList(new ArrayList<TestCaseExecution>());

        TestCaseExecution testCaseExecution = new TestCaseExecution();
		testCaseExecution.setTestName("TestCase1");
        testCaseExecution.setBrowser("Chrome");
        testCaseExecution.setBrowserVersion("90.1");
        testCaseExecution.setScreenResolution("1920*1080");
        testCaseExecution.setStartTime(now);
        testCaseExecution.setEndTime(now.plusSeconds(5));
        testCaseExecution.setTestStatus(TestStatus.PASSED);
        testCaseExecution.getEventList().add(new TestEvent(TestEventType.AUTOMATION, "test content", "Info"));
		testCaseExecution.getEventList().add(new TestEvent(TestEventType.SCREEN_SHOT, "screentshot", "Info", "click", "null", "locator", 1, File.createTempFile("screenshot","")));
		
		testResult.getTestCaseExecutionList().add(testCaseExecution);
		File outputFile = jsonReporter.saveToRegistry(testResult);
		assertTrue(root.toFile().exists());
		assertTrue(root.toFile().isDirectory());
		assertTrue(root.resolve("Screenshots").toFile().isDirectory());
		assertTrue(root.resolve("Screenshots").resolve("00001.png").toFile().exists());
		assertTrue(outputFile.getParent().toString().contains(".testadvisor/TestRun-20210629-135657"));
		assertEquals("test-result.json", outputFile.getName());
	}

}