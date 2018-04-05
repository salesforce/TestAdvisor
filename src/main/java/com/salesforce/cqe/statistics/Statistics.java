/**
 * 
 */
package com.salesforce.cqe.statistics;

/**
 * @author gneumann
 *
 */
public class Statistics {
	public static void main(String[] args) {
		System.out.println("Scanning all result files \"" + args[0] + "*.xlsx\" in directory \"" + args[1] + "\".");
		ExcelStatsHandler statsHandler = new ExcelStatsHandler(args[1]);
		try {
			statsHandler.open(args[0]);
			statsHandler.close();
			System.out.println("Finished scanning result files \"" + args[0] + "*.xlsx\" in directory \"" + args[1] + "\".");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
