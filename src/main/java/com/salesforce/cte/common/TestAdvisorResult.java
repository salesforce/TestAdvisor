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
