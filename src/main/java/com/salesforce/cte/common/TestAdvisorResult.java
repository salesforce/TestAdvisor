/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines test result for a test run
 * 
 * @author Yibing Tao
 */
public class TestAdvisorResult {

    @JsonProperty
    public List<TestCaseExecution> testCaseExecutionList = new ArrayList<>();
    @JsonProperty
    public String version;
    @JsonProperty
    public Instant buildStartTime;
    @JsonProperty
    public Instant buildEndTime;
    
}
