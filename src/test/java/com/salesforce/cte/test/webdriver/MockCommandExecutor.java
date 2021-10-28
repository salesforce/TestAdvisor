/**
 * 
 */
package com.salesforce.cte.test.webdriver;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import org.testng.Assert;

import static org.openqa.selenium.remote.DriverCommand.*;
import static org.testng.Assert.assertNotNull;


/**
 * @author gneumann
 *
 */
public class MockCommandExecutor implements CommandExecutor {
	public static String STATE_OK = "OK";
	public static String STRING_ALLISWELL_VALUE = "All is well";

	private RemoteWebDriver webDriver;
	private String id;
	
	public void setRemoteWebDriver(RemoteWebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public Response execute(Command command) throws IOException {
		Response response = new Response();
    	response.setState(STATE_OK);
   	
	    if (FIND_ELEMENT.equals(command.getName())) {
	    	id = "Elem" + System.currentTimeMillis();
	    	RemoteWebElement rwe = new RemoteWebElement();
	    	rwe.setId(id);
	    	rwe.setParent(webDriver);
	    	response.setValue(rwe);
	    } else if (CLICK_ELEMENT.equals(command.getName())) {
	    	// zero argument command
	    	response.setValue(STRING_ALLISWELL_VALUE);
	    } else if (FIND_CHILD_ELEMENT.equals(command.getName())) {
	    	// one argument command
	    	id = "ChildElem" + System.currentTimeMillis();
	    	RemoteWebElement rwe = new RemoteWebElement();
	    	rwe.setId(id);
	    	rwe.setParent(webDriver);
	    	response.setValue(rwe);
	    } else if (GET_TITLE.equals(command.getName())) {
	    	// zero argument command
	    	response.setValue(STRING_ALLISWELL_VALUE);
	    } else if (GET.equals(command.getName())) {
	    	// one argument command
	    	String url = getStringValueFromParameters(command, "url");
	    	response.setValue(url);
	    } else if (EXECUTE_SCRIPT.equals(command.getName())) {
	    	String script = getStringValueFromParameters(command, "script");
	    	assertNotNull(script);
	    	if (script.contains("style.border='3px solid")) {
	    		response.setValue("highlighted web element");
	    	} else {
	    		response.setValue("script executed");
	    	}
	    } else if (NEW_SESSION.equals(command.getName())) {
	    	Map<String, Object> rawCapabilities = new HashMap<>();
	    	response.setValue(rawCapabilities);
	    	response.setSessionId(MockRemoteWebDriver.DUMMY_SESSION_ID);
	    } else if(SCREENSHOT.equals(command.getName())){
			response.setValue(Base64.getEncoder().encodeToString(STRING_ALLISWELL_VALUE.getBytes()));
		} else if(SUBMIT_ELEMENT.equals(command.getName())) {
			response.setValue(STRING_ALLISWELL_VALUE);
		} else if (SEND_KEYS_TO_ELEMENT.equals(command.getName())){
			response.setValue(STRING_ALLISWELL_VALUE);
		} else if (CLEAR_ELEMENT.equals(command.getName())){
			response.setValue(STRING_ALLISWELL_VALUE);
		}
		else {
	    	System.out.println(String.format("Command %s not yet covered by %s", command.getName(), this.getClass().getName()));
	    }
		return response;
	}
	
	private String getStringValueFromParameters(Command command, String key) {
		String value = null;
		if (command.getParameters() != null) {
			value = (String) command.getParameters().get(key);			
		} else {
			Assert.fail("Command parameters not set");
		}
		return value;
	}
}
