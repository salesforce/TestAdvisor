package com.salesforce.cqe.admin;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

/**
 * 
 * DrillBitAdministratorTest will test all of the methods in the
 * DrillBitAdministrator class to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class DrillBitAdministratorTest {
	
	private DrillBitAdministrator drillbitAdmin = DrillBitAdministrator.getInstance();
	
	/**
	 * Tests to make sure that the default constructor for the DrillBitAdministrator class works as expected
	 */
	@Test
	public void testDrillBitAdministrator() {
        System.out.println("Running the test(s) for the default constructor for the DrillBitAdministrator class");
		assertEquals(0, drillbitAdmin.payloadList.size());
		assertTrue(drillbitAdmin.getClass().toGenericString().startsWith("public class"));
		assertTrue(drillbitAdmin.getClass().toGenericString().endsWith("DrillBitAdministrator"));
	}
	
    /**
     * Tests to make sure that the retrieveRootDirectory() method works as expected
     */
	@Test
    public void testRetrieveRootDirectory() {
		System.out.println("Running the test(s) for retrieveRootDirectory()");
    	// Windows
    	System.setProperty("os.name", "Windows");
    	assertEquals("drillbit", drillbitAdmin.retrieveRootDirectory());
    	// MacOS
    	System.setProperty("os.name", "Mac OS X");
    	assertEquals(".drillbit", drillbitAdmin.retrieveRootDirectory());
    	// Linux
    	System.setProperty("os.name", "Linux");
    	assertEquals(".drillbit", drillbitAdmin.retrieveRootDirectory());
    }

	/**
	 * Tests to make sure that createTestRun() method works as expected
	 */
	@Test
	public void testCreateTestRun() {
		System.out.println("Running the test(s) for createTestRun()");
		fail("Not yet implemented");
	}

	/**
	 * Tests to make sure that createTestExecution() method works as expected
	 */
	@Test
	public void testCreateTestCaseExecution() {
		System.out.println("Running the test(s) for createTestCaseExecution()");
		drillbitAdmin.payloadList.clear();
		assertEquals(0, drillbitAdmin.payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.payloadList.size());
	}

	/**
	 * Tests to make sure that getTestCaseExecution() method works as expected
	 */
	@Test
	public void testGetTestCaseExecution() {
		System.out.println("Running the test(s) for getTestCaseExecution()");
		assertEquals(0, drillbitAdmin.payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.payloadList.size());
		assertEquals(TestCaseExecution.class, drillbitAdmin.getTestCaseExecution().getClass());		
	}
	
//	/**
//	 * Tests to make sure that the saveTestCaseExecution() method works as expected
//	 */
//	@Test
//	public void testSaveTestCaseExecution() {
//		System.out.println("Running the test(s) for saveTestCaseExecution()");
//	    fail("Not yet implemented");
//	}
	
	/**
	 * Tests to make sure that the saveTestCaseExecution() method works as expected
	 * @throws IOException 
	 */
	@Test
	public void testSaveTestCaseExecutionList() throws IOException {
		System.out.println("Running the test(s) for saveTestCaseExecutionList()");

		TestCaseExecution testCaseOne = drillbitAdmin.createTestCaseExecution();
		testCaseOne.setTestName("Test 1");
		testCaseOne.saveEndTime();
		assertEquals("Test 1", drillbitAdmin.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseTwo = drillbitAdmin.createTestCaseExecution();
		testCaseTwo.setTestName("Test 2");
		testCaseTwo.saveEndTime();
		assertEquals("Test 2", drillbitAdmin.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseThree = drillbitAdmin.createTestCaseExecution();
		testCaseThree.setTestName("Test 3");
		testCaseThree.saveEndTime();
		assertEquals("Test 3", drillbitAdmin.getTestCaseExecution().getTestName());
		
		File outputFile = drillbitAdmin.saveTestCaseExecutionList();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
		
		assertTrue(outputFile.getAbsolutePath().toString().contains("/var/folders/6q/xrc0l4q55ml64gxftyh2krlw0000gp/T/"));
		assertTrue(outputFile.getAbsolutePath().toString().contains(".drillbit/TestRun-" + formattedDate.substring(0, 13)));
		assertEquals("test-result.json", outputFile.getName());
		
	}
	
}
