/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

 package com.salesforce.cte.admin;

public class TestAdvisorSwitch {

    private static final String CAPTURE_SCREENHSOT_PROPERTY = "testadvisor.capturescreenshot";

    //private constructor to prevent instance
    private TestAdvisorSwitch() {}

    public static String getOS() {
        return System.getProperty("os.name");
    }

    public static String getUserDirectory() {
        return System.getProperty("user.dir");
    }

    public static boolean getScreenshotCaptureEnabled(){
        return Boolean.parseBoolean(System.getProperty(CAPTURE_SCREENHSOT_PROPERTY,"false"));
    }
    
}
