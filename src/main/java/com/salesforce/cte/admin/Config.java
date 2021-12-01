/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

 package com.salesforce.cte.admin;

public class Config {

    public String getOS() {
        return System.getProperty("os.name");
    }

    public String getUserDirectory() {
        return System.getProperty("user.dir");
    }
    
}
