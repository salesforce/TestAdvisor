package com.salesforce.cqe.admin;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author gpahuja
 *
 */
public class JsonReporterTest {

	private JsonReporter jsonReporter = new JsonReporter();
	
	/**
	 * Test method for {@link com.salesforce.cqe.admin.JsonReporter#saveToRegistry(java.util.ArrayList)}.
	 */
	@Test
	public void testSaveToRegistry() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.salesforce.cqe.admin.JsonReporter#retrieveRootDirectory()}.
	 */
	@Test
    public void testRetrieveRootDirectory() {
    	// Windows
    	System.setProperty("os.name", "Windows");
    	assertEquals("/drillbit", jsonReporter.retrieveRootDirectory());
    	// MacOS
    	System.setProperty("os.name", "Mac OS X");
    	assertEquals(".drillbit", jsonReporter.retrieveRootDirectory());
    	// Linux
    	System.setProperty("os.name", "Linux");
    	assertEquals(".drillbit", jsonReporter.retrieveRootDirectory());
    }

}
