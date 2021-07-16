package com.salesforce.cqe.configuration;

import org.junit.Test;

/**
 *
 * ConfigurationTest will test all of the methods in the
 * Configuration class to make sure that they run and perform as expected
 *
 * @author gpahuja
 *
 */
public class ConfigurationTest {
	
    /**
     * Tests to make sure that the load() method works as expected
     */
	@Test(expected = UnsupportedOperationException.class)
	public void testLoad() {
		Configuration.load();
	}

}
