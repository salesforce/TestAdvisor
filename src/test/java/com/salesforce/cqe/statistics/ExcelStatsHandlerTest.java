/**
 * 
 */
package com.salesforce.cqe.statistics;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.util.List;

import org.testng.annotations.AfterClass;

/**
 * Excel file handling:
 * ====================
 * 1) Open all files with prefix [customerSuite]
 * 2) Write summary file with prefix [customerSuite]
 * 
 * Result retrieval:
 * =================
 * 1) Result object must have all non-empty/non-zero fields
 * 2) getResults() returns non-null list for customerSuite if no results have yet been made available
 * 3) getResults() returns all results for customerSuite if less than MAX_RESULTS results are available
 * 4) getResults() returns MAX_RESULTS results for customerSuite if more than MAX_RESULTS results are available
 * 5) getResult() returns correct result if present
 * 6) getResult() returns NULL if no result could be found
 * 
 * @author gneumann
 */
public class ExcelStatsHandlerTest {
	private static final String EXCEL_RESOURCE_DIR = "test-resources" + File.separator + "excel-files";

	private static ExcelStatsHandler statsHandler;

	@AfterClass
	public void afterClass() {
	}

	@Test(dependsOnGroups = { "open" })
	public void getResult() {
		final String testCaseName = "THD-CRM:View STH Fulfillment Order";
		final int buildId = 12;
		Result resultFound = statsHandler.getResult(testCaseName, buildId);
		assertNotNull(resultFound, "Could not find result for test case '" + testCaseName + "' and build '" + buildId + "'");
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultNonExistingTestCase() {
		final String testCaseName = "foo";
		final int buildId = 12;
		Result resultFound = statsHandler.getResult(testCaseName, buildId);
		assertTrue(resultFound == null);
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultTestCaseIsNull() {
		final String testCaseName = null;
		final int buildId = 12;
		Result resultFound = statsHandler.getResult(testCaseName, buildId);
		assertTrue(resultFound == null);
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultTestCaseIsEmpty() {
		final String testCaseName = "";
		final int buildId = 12;
		Result resultFound = statsHandler.getResult(testCaseName, buildId);
		assertTrue(resultFound == null);
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultBuildIdIsNull() {
		final String testCaseName = "THD-CRM:View STH Fulfillment Order";
		final int buildId = 0;
		Result resultFound = statsHandler.getResult(testCaseName, buildId);
		assertTrue(resultFound == null);
	}

	@Test(dependsOnGroups = { "open" })
	public void getResults() {
		final String testCaseName = "THD-CRM:View STH Fulfillment Order";
		List<Result> resultsFound = statsHandler.getResults(testCaseName);
		assertTrue(resultsFound.size() == 3, "Could not find all 3 results for test case '" + testCaseName + "'");
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultsTestCaseIsNull() {
		final String testCaseName = null;
		List<Result> resultsFound = statsHandler.getResults(testCaseName);
		assertTrue(resultsFound.size() == 0);
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultsTestCaseIsEmpty() {
		final String testCaseName = "";
		List<Result> resultsFound = statsHandler.getResults(testCaseName);
		assertTrue(resultsFound.size() == 0);
	}

	@Test(dependsOnGroups = { "open" })
	public void getTestCaseNames() {
		List<String> testCaseNamesFound = statsHandler.getTestCaseNames();
		assertTrue(testCaseNamesFound.size() == 246, "Could not find all 246 test case names.");
	}

	@Test(dependsOnGroups = { "open" })
	public void getBuildIds() {
		List<Integer> buildIdsFound = statsHandler.getBuildIds();
		assertTrue(buildIdsFound.size() == 3, "Could not find all 3 build ID's.");
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultsByBuildId() {
		List<Result> resultsFound = statsHandler.getResults(31);
		assertTrue(resultsFound.size() == 244, "Could not find all 244 results for build ID 31.");
	}

	@Test(dependsOnGroups = { "open" })
	public void getResultsByBuildIdAndState() {
		List<Result> resultsFound = statsHandler.getResults(31, Result.State.FAIL);
		assertTrue(resultsFound.size() == 44, "Could not find all 44 results for passing tests in build ID 31.");
	}

	@Test(groups = { "open" })
	public void open() {
		statsHandler = new ExcelStatsHandler(EXCEL_RESOURCE_DIR);
		assertNotNull(statsHandler);
		try {
			statsHandler.open("HomeDepot-CRM-Source");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void openNonExistingCustomerSuite() throws Exception {
		ExcelStatsHandler statsHandlerForNonExistingResultFiles = new ExcelStatsHandler(EXCEL_RESOURCE_DIR);
		assertNotNull(statsHandlerForNonExistingResultFiles);
		// Verify exception is thrown when using a "customerSuite" for which no result files can be found
		statsHandlerForNonExistingResultFiles.open("Foo");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void openNullCustomerSuite() throws Exception {
		ExcelStatsHandler statsHandlerForNonExistingResultFiles = new ExcelStatsHandler(EXCEL_RESOURCE_DIR);
		assertNotNull(statsHandlerForNonExistingResultFiles);
		// Verify exception is thrown when using a NULL for "customerSuite"
		statsHandlerForNonExistingResultFiles.open(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void openNonExistingResultsDirectory() {
		new ExcelStatsHandler("test-resources" + File.separator + "foo");
	}

	@Test(dependsOnGroups = { "open" })
	public void close() {
		try {
			statsHandler.close();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
