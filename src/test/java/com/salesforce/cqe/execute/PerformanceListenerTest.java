/**
 * 
 */
package com.salesforce.cqe.execute;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.cqe.execute.selenium.PerformanceListener;
import com.salesforce.selenium.support.event.Step;

/**
 * @author gneumann
 *
 */
public class PerformanceListenerTest {
	private PerformanceListener perfListener;

	@BeforeMethod
	public void setUp() {
		perfListener = new PerformanceListener();
	}

	@Test
	public void testBeforeActionNoParam() {
		Step stepBefore = new Step(Step.Type.BeforeAction, 1, Step.Cmd.close);
		perfListener.beforeClose(stepBefore);
		Step stepAfter = new Step(Step.Type.AfterAction, 1, Step.Cmd.close);
		perfListener.afterClose(stepAfter);
	}

	@AfterMethod
	public void tearDown() {
		perfListener.closeListener();
	}
}
