package com.salesforce.cte.admin;

import static org.junit.Assert.*;

import com.salesforce.cte.admin.Config;

import org.junit.Test;

/**
 *
 * ConfigTest will test all of the methods in the Config class
 * to make sure that they run and perform as expected
 *
 * @author gpahuja
 *
 */
public class ConfigTest {

	private Config config = new Config();
	
    /**
     * Tests to make sure that the getOS() method works as expected
     */
	@Test
	public void testGetOS() {
		assertEquals(System.getProperty("os.name"), config.getOS());
	}

    /**
     * Tests to make sure that the getUserDirectory() method works as expected
     */
	@Test
	public void testGetUserDirectory() {
		assertEquals(System.getProperty("user.dir"), config.getUserDirectory());
		//assertTrue(String.format("user.dir=%s,user.name=%s", config.getUserDirectory(), config.getUserDirectory()), 
		//		config.getUserDirectory().contains(System.getProperty("user.name")));
	}

}
