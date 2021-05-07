/**
 * 
 */
package com.salesforce.cqe.test.webdriver;

import java.io.IOException;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;

import static org.openqa.selenium.remote.DriverCommand.*;


/**
 * @author gneumann
 *
 */
public class MockCommandExecutor implements CommandExecutor {
	public static String STATE_OK = "OK";
	public static String STRING_VALUE = "All is well";

	@Override
	public Response execute(Command command) throws IOException {
		System.out.println(command.toString());
		Response response = new Response();
	    if (GET_TITLE.equals(command.getName())) {
	    	response.setState(STATE_OK);
	    	response.setValue(STRING_VALUE);
	    } else if (GET.equals(command.getName())) {
	    	response.setState(STATE_OK);
	    }
		return response;
	}

}
