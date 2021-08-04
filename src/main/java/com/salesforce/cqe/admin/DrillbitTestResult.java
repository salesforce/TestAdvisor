package com.salesforce.cqe.admin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrillbitTestResult {

    @JsonProperty
    public List<TestCaseExecution> payloadList = new ArrayList<>();
    @JsonProperty
    public String version;
    @JsonProperty
    public String buildStartTime;
    @JsonProperty
    public String buildEndTime;
    
}
