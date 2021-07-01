package com.salesforce.cqe.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
	
	@Before
	public void createFolderStructure() throws IOException {
		root = Files.createTempDirectory("").resolve(".drillbit").resolve("TestRun-20210629-135657");
		
		root.toFile().mkdirs();
		
		jsonReporter = new JsonReporter(root.toString());
	}
	
    /**
     * Tests to make sure that the saveToRegistry() method works as expected
     * @throws IOException if an I/O error occurs or if the temporary-file directory doesn't exist
     */
	@Test
	public void testSaveToRegistry() throws IOException {
		System.out.println("Running the test(s) for saveToRegistry()");
		System.setProperty("os.name", "Mac OS X");
		
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
		
		File outputFile = jsonReporter.saveToRegistry(payloadList);

		System.out.println("Temporary Root Directory: " + root.toString());
		System.out.println("Absolute JSON File Path: " + outputFile.getAbsolutePath());
		
		assertTrue(root.toFile().exists());
		assertTrue(root.toFile().isDirectory());
		assertTrue(outputFile.getParent().toString().contains("/var/folders/6q/xrc0l4q55ml64gxftyh2krlw0000gp/T/"));
		assertTrue(outputFile.getParent().toString().contains(".drillbit/TestRun-20210629-135657"));
		assertEquals("test-result.json", outputFile.getName());
	}

}