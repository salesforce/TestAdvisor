/**
 * 
 */
package com.salesforce.cqe.copado;

/**
 * @author gneumann
 *
 */
public class CustomerCopado {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ResultManager.Result result = ResultManager.getResult(ResultManager.Data.getData());
			if (result != null) {
				CQEReport.save(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
