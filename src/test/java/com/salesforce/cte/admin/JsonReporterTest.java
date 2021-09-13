package com.salesforce.cte.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.salesforce.cte.common.TestAdvisorResult;
import com.salesforce.cte.common.TestCaseExecution;

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
		testResult.payloadList = payloadList;
		
		File outputFile = jsonReporter.saveToRegistry(testResult);
		assertTrue(root.toFile().exists());
		assertTrue(root.toFile().isDirectory());
		assertTrue(outputFile.getParent().toString().contains(".testadvisor/TestRun-20210629-135657"));
		assertEquals("test-result.json", outputFile.getName());
	}

}