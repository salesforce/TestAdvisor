/**
 * 
 */
package com.salesforce.cte.test.webdriver;

import static org.testng.Assert.*;

import java.util.List;

import com.salesforce.cte.common.JsonHelper;
import com.salesforce.cte.listener.selenium.EventDispatcher;
import com.salesforce.cte.listener.selenium.FullLogger;
import com.salesforce.cte.listener.selenium.IEventListener;
import com.salesforce.cte.listener.selenium.ScreenshotLogger;
import com.salesforce.cte.listener.testng.TestListener;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @author gneumann
 *
 */
@Listeners(TestListener.class)
public class TestEventDispatching {
	private static MockRemoteWebDriver wd;
	private static FullLogger fullLogger;
	private static ScreenshotLogger screenshotLogger;

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
		List<IEventListener> eventListeners = EventDispatcher.getInstance(wd).getImmutableListOfEventListeners();
		for (IEventListener listener : eventListeners) {
			if (listener instanceof FullLogger) {
				fullLogger = (FullLogger) listener;
			}
			if (listener instanceof ScreenshotLogger){
				screenshotLogger = (ScreenshotLogger) listener;
			}
		}
	}
	
	@Test
	public void testFindElementByWebDriver() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		assertNumOfLogEntries("findElementByWebDriver", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
		
	}
	
	@Test
	public void testClick() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		int numOfScreenshotEventsBefore = screenshotLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		we.click();
		assertNumOfLogEntries("click", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 6);
		assertNumOfLogEntries("click", numOfScreenshotEventsBefore, screenshotLogger.getListOfEventsRecorded().size(), 1);
	}
	
	@Test
	public void testFindElementByElement() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		WebElement childWe = we.findElement(By.id("someOtherId"));
		assertNotNull(childWe);
		assertNumOfLogEntries("findElementByElement", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 4);
	}
	
	@Test
	public void testClickByChildElement() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		WebElement childWe = we.findElement(By.id("someOtherId"));
		assertNotNull(childWe);
		childWe.click();
		assertNumOfLogEntries("clickByChildElement", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 8);
	}

	@Test
	public void testGet() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		wd.get("https://www.salesforce.com");
		assertNumOfLogEntries("get", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
	}

	@Test
	public void testGetTitle() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		assertEquals(MockCommandExecutor.STRING_ALLISWELL_VALUE, wd.getTitle());
		assertNumOfLogEntries("getTitle", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 2);
	}

	@Test
	public void testWriteEventsToDisk() {
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		final long timeStamp = System.currentTimeMillis();
		try {
			JsonHelper.toFile(timeStamp +".json", fullLogger.getListOfEventsRecorded());
		} catch (Exception e) {
			Assert.fail("writing full log details failed", e);
		}
	}

	@Test
	public void testExecuteScript() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		int numOfScreenshotEventsBefore = screenshotLogger.getListOfEventsRecorded().size();
		wd.executeScript("click");
		assertNumOfLogEntries("click", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 4);
		assertNumOfLogEntries("click", numOfScreenshotEventsBefore, screenshotLogger.getListOfEventsRecorded().size(), 1);
	}

	@Test
	public void testSubmit() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		int numOfScreenshotEventsBefore = screenshotLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		we.submit();
		assertNumOfLogEntries("submit", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 6);
		assertNumOfLogEntries("submit", numOfScreenshotEventsBefore, screenshotLogger.getListOfEventsRecorded().size(), 1);
	}

	@Test
	public void testSendKeysByElement() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		int numOfScreenshotEventsBefore = screenshotLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		we.sendKeys("abc");
		assertNumOfLogEntries("sendkeys", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 6);
		assertNumOfLogEntries("sendkeys", numOfScreenshotEventsBefore, screenshotLogger.getListOfEventsRecorded().size(), 1);
	}

	@Test
	public void testClear() {
		int numOfEventsBefore = fullLogger.getListOfEventsRecorded().size();
		int numOfScreenshotEventsBefore = screenshotLogger.getListOfEventsRecorded().size();
		WebElement we = wd.findElement(By.id("someId"));
		assertNotNull(we);
		we.clear();
		assertNumOfLogEntries("clear", numOfEventsBefore, fullLogger.getListOfEventsRecorded().size(), 6);
		assertNumOfLogEntries("clear", numOfScreenshotEventsBefore, screenshotLogger.getListOfEventsRecorded().size(), 1);
	}

	private void assertNumOfLogEntries(String command, int before, int after, int expectedDifference) {
		System.out.println(String.format("Number of events logged before %s(): %d, and after: %d", command, before, after));
		assertTrue(after > before);
		assertEquals(expectedDifference, after - before);
	}
}
