/**
 * 
 */
package com.salesforce.cqe.test.webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author gneumann
 *
 */
public class MockRemoteWebDriver extends RemoteWebDriver {
	public MockRemoteWebDriver(MockCommandExecutor mockCommandExecutor, Object object) {
		setCommandExecutor(mockCommandExecutor);
		setSessionId("dummy-session-id");
	}
}
