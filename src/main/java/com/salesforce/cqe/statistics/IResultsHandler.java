/**
 * 
 */
package com.salesforce.cqe.statistics;

import java.util.List;

/**
 * Results handling by file, DB, or any other means.
 * 
 * @author gneumann
 */
public interface IResultsHandler {
	/**
	 * Open connection or file and read results for given customer suite.
	 * @param customerSuite
	 * @throws Exception if IO operation or result parsing encountered a problem.
	 */
	void open(String customerSuite) throws Exception;
	/**
	 * Gets the results for the various builds in which the given test
	 * was executed.
	 * @param testName
	 * @return list of results or empty list
	 */
	List<Result> getResults(String testName);
	/**
	 * Gets the results for all tests executed in the given build.
	 * @param buildId
	 * @return list of results or empty list
	 */
	List<Result> getResults(int buildId);
	/**
	 * Gets the results for all tests executed in the given build
	 * which had the given outcome.
	 * @param buildId
	 * @param state "pass" or "fail"
	 * @return list of results or empty list
	 */
	List<Result> getResults(int buildId, Result.State state);
	/**
	 * Gets the result for the given build in which the given test
	 * was executed.
	 * @param testName
	 * @param buildId
	 * @return result or NULL
	 */
	Result getResult(String testName, int buildID);
	/**
	 * Gets the list of test cases executed in any of the builds.
	 * <p>
	 * In each build different tests might have been executed,
	 * e.g. one or more tests got added in a later build perhaps?
	 * @return list of test case names or empty list
	 */
	List<String> getTestCaseNames();
	/**
	 * Gets the list of builds for which results were reported in the input.
	 * @return list of builds or empty list
	 */
	List<Integer> getBuildIds();
	/**
	 * Writes back a summary, either to a file or a DB.
	 * <p>
	 * Please note: the format of the summary is completely up to the implementor.
	 * @throws Exception problems encountered during writing the summary
	 */
	void close() throws Exception;
}
