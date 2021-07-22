package com.salesforce.cqe.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
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
	
	private DrillBitAdministrator drillbitAdmin;
	
	@Before
	public void beforeEachTestMethod() throws IOException {
		drillbitAdmin = DrillBitAdministrator.getInstance();
		drillbitAdmin.payloadList.clear();
	}

	/**
	 * Tests to make sure that the default constructor for the DrillBitAdministrator class works as expected
	 */
	@Test
	public void testDrillBitAdministrator() {
		assertEquals(0, drillbitAdmin.payloadList.size());
		assertTrue(drillbitAdmin.getClass().toGenericString().startsWith("public class"));
		assertTrue(drillbitAdmin.getClass().toGenericString().endsWith("DrillBitAdministrator"));
	}
	
    /**
     * Tests to make sure that the retrieveRootDirectory() method works as expected
     */
	@Test
    public void testRetrieveRootDirectory() {
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
	 * Tests to make sure that createTestExecution() method works as expected
	 */
	@Test
	public void testCreateTestCaseExecution() {
		assertEquals(0, drillbitAdmin.payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.payloadList.size());
	}

	/**
	 * Tests to make sure that getTestCaseExecution() method works as expected
	 */
	@Test
	public void testGetTestCaseExecution() {
		assertEquals(0, drillbitAdmin.payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.payloadList.size());
		assertEquals(TestCaseExecution.class, drillbitAdmin.getTestCaseExecution().getClass());
	}
	
// TODO: This function tests to make sure that the saveTestCaseExecution() method works as expected
//	@Test
//	public void testSaveTestCaseExecution() {
//	    fail("Not yet implemented");
//	}
	
	/**
	 * Tests to make sure that the saveTestCaseExecution() method works as expected
	 * on a system running Mac OS
	 */
	@Test
	public void testSaveTestCaseExecutionListMac() {
		Config mockConfig = mock(Config.class);
		when(mockConfig.getOS()).thenReturn("Mac OS X");

		DrillBitAdministrator drillbitAdminMac = DrillBitAdministrator.getInstance();

		TestCaseExecution testCaseOne = drillbitAdminMac.createTestCaseExecution();
		testCaseOne.setTestName("Test 1");
		testCaseOne.saveEndTime();
		assertEquals("Test 1", drillbitAdminMac.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseTwo = drillbitAdminMac.createTestCaseExecution();
		testCaseTwo.setTestName("Test 2");
		testCaseTwo.saveEndTime();
		assertEquals("Test 2", drillbitAdminMac.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseThree = drillbitAdminMac.createTestCaseExecution();
		testCaseThree.setTestName("Test 3");
		testCaseThree.saveEndTime();
		assertEquals("Test 3", drillbitAdminMac.getTestCaseExecution().getTestName());
		
		File outputFile = drillbitAdminMac.saveTestCaseExecutionList();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
        
		assertTrue(outputFile.getAbsolutePath().toString().contains("/var/folders/6q/xrc0l4q55ml64gxftyh2krlw0000gp/T/"));
		assertTrue(outputFile.getAbsolutePath().toString().contains(".drillbit/TestRun-" + formattedDate.substring(0, 13)));
		assertEquals("test-result.json", outputFile.getName());
		
		outputFile.deleteOnExit();
	}

	/**
	 * Tests to make sure that the saveTestCaseExecution() method works as expected
	 * on a system running Windows
	 */
	@Test
	public void testSaveTestCaseExecutionListWindows() {
		Config mockConfig = mock(Config.class);
		when(mockConfig.getOS()).thenReturn("Windows 10");

		DrillBitAdministrator drillbitAdminWindows = DrillBitAdministrator.getInstance();

		TestCaseExecution testCaseOne = drillbitAdminWindows.createTestCaseExecution();
		testCaseOne.setTestName("Test 1");
		testCaseOne.saveEndTime();
		assertEquals("Test 1", drillbitAdminWindows.getTestCaseExecution().getTestName());

		TestCaseExecution testCaseTwo = drillbitAdminWindows.createTestCaseExecution();
		testCaseTwo.setTestName("Test 2");
		testCaseTwo.saveEndTime();
		assertEquals("Test 2", drillbitAdminWindows.getTestCaseExecution().getTestName());

		TestCaseExecution testCaseThree = drillbitAdminWindows.createTestCaseExecution();
		testCaseThree.setTestName("Test 3");
		testCaseThree.saveEndTime();
		assertEquals("Test 3", drillbitAdminWindows.getTestCaseExecution().getTestName());

		File outputFile = drillbitAdminWindows.saveTestCaseExecutionList();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		assertTrue(outputFile.getAbsolutePath().toString().contains("/var/folders/6q/xrc0l4q55ml64gxftyh2krlw0000gp/T/"));
		
		outputFile.deleteOnExit();
	}

}
