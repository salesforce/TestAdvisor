/**
 * 
 */
package com.salesforce.cqe.test.webdriver;

import static org.testng.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.salesforce.cqe.driver.EventDispatcher;
import com.salesforce.cqe.driver.listener.FullLogger;
import com.salesforce.cqe.driver.listener.IEventListener;

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
		MutableCapabilities mcap = new MutableCapabilities();
		mcap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, "true");
		MockCommandExecutor mce = new MockCommandExecutor();
		wd = new MockRemoteWebDriver(mce, mcap);
		mce.setRemoteWebDriver(wd);
		List<IEventListener> eventListeners = EventDispatcher.getInstance().getImmutableListOfEventListeners();
		for (IEventListener listener : eventListeners) {
			if (listener instanceof FullLogger) {
				fullLogger = (FullLogger) listener;
				break;
			}
		}
	}
	
	@Test
	public void testFindElementByWebDriver() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		assertNumOfLogEntries("findElementByWebDriver", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
	}
	
	@Test
	public void testClick() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		we.click();
		assertNumOfLogEntries("click", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 4);
	}
	
	@Test
	public void testFindElementByElement() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		WebElement childWe = we.findElement(By.id("someOtherId"));
		assertNotNull(childWe);
		assertNumOfLogEntries("findElementByElement", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 4);
	}
	
	@Test
	public void testClickByChildElement() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		WebElement childWe = we.findElement(By.id("someOtherId"));
		assertNotNull(childWe);
		childWe.click();
		assertNumOfLogEntries("clickByChildElement", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 6);
	}

	@Test
	public void testGet() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		wd.get("https://www.salesforce.com");
		assertNumOfLogEntries("get", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
	}

	@Test
	public void testGetTitle() {
		int numOfStepsBefore = fullLogger.getListOfEventsRecorded().size();
		assertEquals(MockCommandExecutor.STRING_ALLISWELL_VALUE, wd.getTitle());
		assertNumOfLogEntries("getTitle", numOfStepsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
	}
	
	private void assertNumOfLogEntries(String command, int before, int after, int expectedDifference) {
		System.out.println(String.format("Number of steps logged before %s(): %d, and after: %d", command, before, after));
		assertTrue(after > before);
		assertEquals(expectedDifference, after - before);
	}
}
