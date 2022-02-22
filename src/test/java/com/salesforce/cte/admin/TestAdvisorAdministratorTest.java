/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.salesforce.cte.common.TestCaseExecution;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * TestAdvisorAdministratorTest will test all of the methods in the
 * TestAdvisorAdministrator class to make sure that they run and perform as expected
 * 
 * @author gpahuja
 *
 */
public class TestAdvisorAdministratorTest {
	
	private TestAdvisorAdministrator taAdmin;
	
	@Before
	public void beforeEachTestMethod() throws IOException {
		taAdmin = TestAdvisorAdministrator.getInstance();
		taAdmin.getTestResult().getTestCaseExecutionList().clear();
	}

	/**
	 * Tests to make sure that the default constructor for the TestAdvisorAdministrator class works as expected
	 */
	@Test
	public void testTestAdvisorAdministrator() {
		assertEquals(0, taAdmin.getTestResult().getTestCaseExecutionList().size());
		assertTrue(taAdmin.getClass().toGenericString().startsWith("public class"));
		assertTrue(taAdmin.getClass().toGenericString().endsWith("TestAdvisorAdministrator"));
	}
	
    /**
     * Tests to make sure that the retrieveRootDirectory() method works as expected
     */
	@Test
    public void testRetrieveRootDirectoryForWindows() {
		System.setProperty("os.name", "Windows");
    	assertEquals("testadvisor", TestAdvisorAdministrator.getInstance().retrieveRootDirectory());
		System.setProperty("os.name", "Mac OS X");
    	assertEquals(".testadvisor", TestAdvisorAdministrator.getInstance().retrieveRootDirectory());
		System.setProperty("os.name", "Linux");
    	assertEquals(".testadvisor", TestAdvisorAdministrator.getInstance().retrieveRootDirectory());
    }

	/**
	 * Tests to make sure that createTestExecution() method works as expected
	 */
	@Test
	public void testCreateTestCaseExecution() {
		assertEquals(0, taAdmin.getTestResult().getTestCaseExecutionList().size());
		taAdmin.createTestCaseExecution("");
		assertEquals(1, taAdmin.getTestResult().getTestCaseExecutionList().size());
	}

	/**
	 * Tests to make sure that getTestCaseExecution() method works as expected
	 */
	@Test
	public void testGetTestCaseExecution() {
		assertEquals(0, taAdmin.getTestResult().getTestCaseExecutionList().size());
		taAdmin.createTestCaseExecution("");
		assertEquals(1, taAdmin.getTestResult().getTestCaseExecutionList().size());
		assertEquals(TestCaseExecution.class, taAdmin.getTestCaseExecution().getClass());
	}
	
	/**
	 * Tests to make sure that the saveTestCaseExecution() method works as expected
	 * on a system running Mac OS
	 * @throws IOException
	 */
	@Test
	public void testSaveTestCaseExecutionListMac() throws IOException {
		System.setProperty("os.name", "Mac OS X");
		TestAdvisorAdministrator taAdminMac = TestAdvisorAdministrator.getInstance();
		TestCaseExecution testCaseOne = taAdminMac.createTestCaseExecution("Test 1");
		testCaseOne.saveEndTime();
		assertEquals("Test 1", taAdminMac.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseTwo = taAdminMac.createTestCaseExecution("Test 2");
		testCaseTwo.saveEndTime();
		assertEquals("Test 2", taAdminMac.getTestCaseExecution().getTestName());
		
		TestCaseExecution testCaseThree = taAdminMac.createTestCaseExecution("Test 3");
		testCaseThree.saveEndTime();
		assertEquals("Test 3", taAdminMac.getTestCaseExecution().getTestName());
		
		File outputFile = taAdminMac.saveTestResult();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDate = localDateTime.format(formatter);
        
		assertTrue(outputFile.getAbsolutePath().toString().contains(".testadvisor/TestRun-" + formattedDate.substring(0, 13)));
		assertEquals("test-result.json", outputFile.getName());
		
		outputFile.deleteOnExit();
	}

	/**
	 * Tests to make sure that the saveTestCaseExecution() method works as expected
	 * on a system running Windows
	 * @throws IOException
	 */
	@Test
	public void testSaveTestCaseExecutionListWindows() throws IOException {
		System.setProperty("os.name", "Windows 10");
		TestAdvisorAdministrator taAdminWindows = TestAdvisorAdministrator.getInstance();
		TestCaseExecution testCaseOne = taAdminWindows.createTestCaseExecution("Test 1");
		testCaseOne.saveEndTime();
		assertEquals("Test 1", taAdminWindows.getTestCaseExecution().getTestName());

		TestCaseExecution testCaseTwo = taAdminWindows.createTestCaseExecution("Test 2");
		testCaseTwo.saveEndTime();
		assertEquals("Test 2", taAdminWindows.getTestCaseExecution().getTestName());

		TestCaseExecution testCaseThree = taAdminWindows.createTestCaseExecution("Test 3");
		testCaseThree.saveEndTime();
		assertEquals("Test 3", taAdminWindows.getTestCaseExecution().getTestName());

		File outputFile = taAdminWindows.saveTestResult();

		assertTrue(outputFile.exists());
		assertTrue(outputFile.getParentFile().isDirectory());
		
		assertTrue(outputFile.getAbsolutePath().toString().contains("testadvisor/TestRun-"));
		
		outputFile.deleteOnExit();
	}

	@Test
	public void versionTest(){
		TestAdvisorAdministrator admin = TestAdvisorAdministrator.getInstance();
		Pattern pattern = Pattern.compile("(\\d+.\\d+.*)");
		Matcher matcher = pattern.matcher(admin.getVersion());
		assertTrue(matcher.find());
	}

}
