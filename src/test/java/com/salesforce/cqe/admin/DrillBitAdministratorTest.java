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
		drillbitAdmin.getTestResult().payloadList.clear();
	}

	/**
	 * Tests to make sure that the default constructor for the DrillBitAdministrator class works as expected
	 */
	@Test
	public void testDrillBitAdministrator() {
		assertEquals(0, drillbitAdmin.getTestResult().payloadList.size());
		assertTrue(drillbitAdmin.getClass().toGenericString().startsWith("public class"));
		assertTrue(drillbitAdmin.getClass().toGenericString().endsWith("DrillBitAdministrator"));
	}
	
    /**
     * Tests to make sure that the retrieveRootDirectory() method works as expected
     */
	@Test
    public void testRetrieveRootDirectoryForWindows() {
		Config mockConfig = mock(Config.class);
		when(mockConfig.getOS()).thenReturn("Windows").thenReturn("Mac OS X").thenReturn("Linux");
		DrillBitAdministrator.getInstance().setConfig(mockConfig);
    	assertEquals("drillbit", DrillBitAdministrator.getInstance().retrieveRootDirectory());
    	assertEquals(".drillbit", DrillBitAdministrator.getInstance().retrieveRootDirectory());
    	assertEquals(".drillbit", DrillBitAdministrator.getInstance().retrieveRootDirectory());
    }

	/**
	 * Tests to make sure that createTestExecution() method works as expected
	 */
	@Test
	public void testCreateTestCaseExecution() {
		assertEquals(0, drillbitAdmin.getTestResult().payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.getTestResult().payloadList.size());
	}

	/**
	 * Tests to make sure that getTestCaseExecution() method works as expected
	 */
	@Test
	public void testGetTestCaseExecution() {
		assertEquals(0, drillbitAdmin.getTestResult().payloadList.size());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(1, drillbitAdmin.getTestResult().payloadList.size());
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
		drillbitAdminMac.setConfig(mockConfig);
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
		
		File outputFile = drillbitAdminMac.saveTestResult();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
        
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
		drillbitAdminWindows.setConfig(mockConfig);
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

		File outputFile = drillbitAdminWindows.saveTestResult();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		assertTrue(outputFile.getAbsolutePath().toString().contains("drillbit/TestRun-"));
		
		outputFile.deleteOnExit();
	}

}
