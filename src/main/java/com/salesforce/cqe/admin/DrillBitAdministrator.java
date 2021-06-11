package com.salesforce.cqe.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DrillBitAdministrator {

    @JsonProperty
    public List<TestCaseExecution> payloadList;

}
