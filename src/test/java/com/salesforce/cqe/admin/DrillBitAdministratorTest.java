package com.salesforce.cqe.admin;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author gpahuja
 *
 */
public class DrillBitAdministratorTest {
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	private DrillBitAdministrator drillbitAdmin = DrillBitAdministrator.getInstance();

	@Test
	public void testDrillBitAdministrator() {
		assertEquals(0, drillbitAdmin.payloadList.size());
	}

	/**
	 * Tests to make sure that createTestRun() method works as expected
	 */
	@Test
	public void testCreateTestRun() {
		fail("Not yet implemented");
	}

	/**
	 * Tests to make sure that createTestExecution() method works as expected
	 */
	@Test
	public void testCreateTestCaseExecution() {
		assertEquals(0, drillbitAdmin.payloadList.size());
		assertEquals(TestCaseExecution.class, drillbitAdmin.createTestCaseExecution().getClass());
		drillbitAdmin.createTestCaseExecution();
		assertEquals(2, drillbitAdmin.payloadList.size());
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

	/**
	 * Tests to make sure that saveTestCaseExecution() method works as expected
	 */
	@Test
	public void testSaveTestCaseExecution() {
		fail("Not yet implemented");
	}

}
