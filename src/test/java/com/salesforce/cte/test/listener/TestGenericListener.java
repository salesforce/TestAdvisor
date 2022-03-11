/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

 package com.salesforce.cte.test.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.salesforce.cte.admin.TestAdvisorAdministrator;
import com.salesforce.cte.common.TestStatus;
import com.salesforce.cte.listener.GenericTestListener;

import org.testng.annotations.Test;

public class TestGenericListener {
    
    @Test
    public void testTestRun(){
        TestAdvisorAdministrator admin = TestAdvisorAdministrator.getInstance();
        admin.getTestResult().getTestCaseExecutionList().clear();

        GenericTestListener listener = new GenericTestListener();
        listener.onTestRunStart();
        listener.onTestConfigurationStart("setupTestName");
        listener.onTestConfigurationEnd();
        listener.onTestCaseStart("testName");
        listener.onTestCaseEvent("eventContent", Level.INFO);
        listener.onTestCaseException(new Exception("my exception"));
        listener.onTestCaseStatus(TestStatus.FAILED);
        listener.onTestCaseEnd();
        listener.onTestRunEnd();

        assertEquals(2, admin.getTestResult().getTestCaseExecutionList().size());
        assertTrue( admin.getTestResult().getTestCaseExecutionList().get(0).isConfiguration());
        assertTrue( !admin.getTestResult().getTestCaseExecutionList().get(1).isConfiguration());
        assertEquals(TestStatus.PASSED, admin.getTestResult().getTestCaseExecutionList().get(0).getTestStatus());
        assertEquals(TestStatus.FAILED, admin.getTestResult().getTestCaseExecutionList().get(1).getTestStatus());
        admin.getTestResult().getTestCaseExecutionList().clear();
    }

    @Test
    public void testParallelTestRun() throws InterruptedException{
        TestAdvisorAdministrator admin = TestAdvisorAdministrator.getInstance();
        admin.getTestResult().getTestCaseExecutionList().clear();

        GenericTestListener listener = new GenericTestListener();
        int threadCount = 8;
        listener.onTestRunStart();
        List<Thread> threadList = new ArrayList<>();
        for (int i=0;i<threadCount;i++){
            threadList.add(new Thread(new TestRun(listener)));
        }
        for (int i=0;i<threadCount;i++){
            threadList.get(i).start();
        }
        for (int i=0;i<threadCount;i++){
            threadList.get(i).join();
        }
        listener.onTestRunEnd();

        assertEquals(threadCount*2, admin.getTestResult().getTestCaseExecutionList().size());
        admin.getTestResult().getTestCaseExecutionList().clear();
    }

    class TestRun implements Runnable{
        GenericTestListener listener;

        public TestRun(GenericTestListener listener){
            this.listener = listener;
        }

        @Override
        public void run() {
            listener.onTestConfigurationStart("setupTestName");
            listener.onTestConfigurationEnd();
            listener.onTestCaseStart("testName");
            listener.onTestCaseEvent("eventContent", Level.INFO);
            listener.onTestCaseException(new Exception("my exception"));
            listener.onTestCaseStatus(TestStatus.PASSED);
            listener.onTestCaseEnd();
        }

    }
}
 