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

/**
 * Defines test result for a test run
 * 
 * @author Yibing Tao
 */
public class TestAdvisorResult {

    private List<TestCaseExecution> testCaseExecutionList = new ArrayList<>();
    private String version = "";
    private Instant buildStartTime;
    private Instant buildEndTime;
    
    public List<TestCaseExecution> getTestCaseExecutionList() {
        return testCaseExecutionList;
    }
    public Instant getBuildEndTime() {
        return buildEndTime;
    }
    public void setBuildEndTime(Instant buildEndTime) {
        this.buildEndTime = buildEndTime;
    }
    public Instant getBuildStartTime() {
        return buildStartTime;
    }
    public void setBuildStartTime(Instant buildStartTime) {
        this.buildStartTime = buildStartTime;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setTestCaseExecutionList(List<TestCaseExecution> testCaseExecutionList) {
        this.testCaseExecutionList = testCaseExecutionList;
    }
    
}
