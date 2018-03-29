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
	void open(String customerSuite) throws Exception;
	List<Result> getResults(String testName);
	Result getResult(String testName, int buildID);
	List<String> getTestCaseNames();
	List<Integer> getBuildIds();
	void close() throws Exception;
}
