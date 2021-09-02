/**
 * 
 */
package com.salesforce.cte.test.webdriver;

import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;

import com.google.common.collect.ImmutableMap;

/**
 * @author gneumann
 *
 */
public class MockRemoteWebDriver extends RemoteWebDriver {
	public static final String DUMMY_SESSION_ID = "dummy-session-id";
	
	public MockRemoteWebDriver(MockCommandExecutor mockCommandExecutor, Capabilities caps) {
		super(mockCommandExecutor, caps);
		setCommandExecutor(mockCommandExecutor);
		setSessionId(DUMMY_SESSION_ID);
	}
	
	protected Response execute(String command, Map<String, ?> parameters) {
		return super.execute(command, parameters);
	}

	protected Response execute(String command) {
		return execute(command, ImmutableMap.of());
	}
}
