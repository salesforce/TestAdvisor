/**
 * 
 */
package com.salesforce.cqe.test.webdriver;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.salesforce.cqe.driver.EventDispatcher;
import com.salesforce.cqe.driver.listener.FullLogger;
import com.salesforce.cqe.driver.listener.IEventListener;
import com.salesforce.cqe.driver.listener.Step;

/**
 * @author gneumann
 *
 */
public class TestEventDispatching {
	private static MockRemoteWebDriver wd;
	private static FullLogger fullLogger;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wd = new MockRemoteWebDriver(new MockCommandExecutor(), null);
		List<IEventListener> eventListeners = EventDispatcher.getInstance().getImmutableListOfEventListeners();
		for (IEventListener listener : eventListeners) {
			if (listener instanceof FullLogger) {
				fullLogger = (FullLogger) listener;
				break;
			}
		}
		
	}

	@Test
	public void testGet() {
		List<Step> before = fullLogger.getImmutableListOfSteps();
		int numOfStepsBefore = before.size();
		wd.get("https://www.salesforce.com");
		List<Step> after = fullLogger.getImmutableListOfSteps();
		int numOfStepsAfter = after.size();
		System.out.println(String.format("Number of steps logged before command: %d, and after: %d", numOfStepsBefore, numOfStepsAfter));
		assertTrue(numOfStepsAfter > numOfStepsBefore);
	}

	@Test
	public void testGetTitle() {
		assertEquals(MockCommandExecutor.STRING_VALUE, wd.getTitle());
	}
}
