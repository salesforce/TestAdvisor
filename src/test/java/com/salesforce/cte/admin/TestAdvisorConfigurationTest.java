/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * ConfigTest will test all of the methods in the Config class
 * to make sure that they run and perform as expected
 *
 * @author gpahuja
 *
 */
public class TestAdvisorConfigurationTest {

	
    /**
     * Tests to make sure that the getOS() method works as expected
     */
	@Test
	public void testGetOS() {
		assertEquals(System.getProperty("os.name"), TestAdvisorConfiguration.getOS());
	}

    /**
     * Tests to make sure that the getUserDirectory() method works as expected
     */
	@Test
	public void testGetUserDirectory() {
		assertEquals(System.getProperty("user.dir"), TestAdvisorConfiguration.getUserDirectory());
	}

	@Test
	public void testGetIsScreenshotCaptureEnabled(){
		//by default, screenshot capture is off
		System.clearProperty("testadvisor.capturescreenshot");
		assertTrue(!TestAdvisorConfiguration.getIsScreenshotCaptureEnabled());

		System.setProperty("testadvisor.capturescreenshot", "true");
		assertTrue(TestAdvisorConfiguration.getIsScreenshotCaptureEnabled());

		System.setProperty("testadvisor.capturescreenshot", "false");
		assertTrue(!TestAdvisorConfiguration.getIsScreenshotCaptureEnabled());

		System.setProperty("testadvisor.capturescreenshot", "");
		assertTrue(!TestAdvisorConfiguration.getIsScreenshotCaptureEnabled());

	}

}
