/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cte.test.listener;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.salesforce.cte.listener.testng.TestListener;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(TestListener.class)
public class TestTestNGListener {

    @BeforeTest
    public void beforeTest() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
        //assertTrue("Failed before test", false);
    }

    @BeforeClass
    public void beforeClass() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
        //assertTrue("Failed before class", false);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
        //assertTrue("Failed before method", false);
    }
    
    @BeforeMethod
    public void beforeMethod2() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
    }

    @Test
    public void testTestCasePassed() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);        
        assertTrue("Passed test case", true);
    }

    @Test(expectedExceptions = { IOException.class })
    public void testTestCaseFailed() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
        throw new IOException("Test NoSuchElement Exception");
        
    }

    @AfterTest
    public void afterTest(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);       
    }

    @AfterMethod
    public void afterMethod2(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
    }

    @AfterMethod
    public void afterMethod(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
    }

    @AfterClass 
    public void afterClass(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        printMsg(methodName);
    }

    private void printMsg(String msg){
        System.out.println(String.format("thread:%d -- %s",Thread.currentThread().getId(),msg));
    }

}
