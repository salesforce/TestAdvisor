/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.common;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Test;

public class TestAdvisorResultTest {
    
    @Test
    public void testSerialization() throws StreamWriteException, DatabindException, IOException{
        Instant now = Instant.now();
        TestAdvisorResult result = new TestAdvisorResult();
        result.setVersion("1.1.1-test");
        result.setBuildEndTime(now.minusSeconds(100));
        result.setBuildStartTime(now.minusSeconds(200));

        result.getTestCaseExecutionList().add(new TestCaseExecution());
        result.getTestCaseExecutionList().get(0).appendEvent(new TestEvent("event content","INFO"));
        result.getTestCaseExecutionList().get(0).appendEvent(new TestEvent("event content","INFO","","","",0,new File("somefile")));

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());   
        ByteArrayOutputStream output = new ByteArrayOutputStream(); 
        
        objectWriter.withDefaultPrettyPrinter().writeValue(output, result);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        TestAdvisorResult result2 =  objectMapper.readValue(input, TestAdvisorResult.class);

        assertEquals(result.getVersion(), result2.getVersion());
        assertEquals(result.getBuildEndTime(), result2.getBuildEndTime());
        assertEquals(result.getBuildStartTime(), result2.getBuildStartTime());
        assertEquals(result.getTestCaseExecutionList().size(), result2.getTestCaseExecutionList().size());
        assertEquals(result.getTestCaseExecutionList().get(0).getBrowser(), result2.getTestCaseExecutionList().get(0).getBrowser());
        assertEquals(result.getTestCaseExecutionList().get(0).getEventList().size(), result2.getTestCaseExecutionList().get(0).getEventList().size());
        assertEquals(result.getTestCaseExecutionList().get(0).getEventList().get(0).getEventSource(), result2.getTestCaseExecutionList().get(0).getEventList().get(0).getEventSource());

    }
}
