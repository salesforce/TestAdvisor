package com.salesforce.cte.test.listener;

import static org.junit.Assert.assertTrue;

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
public class TestProviderListener {

    @BeforeTest
    public void beforeTest() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        //assertTrue("Failed before test", false);
    }

    @BeforeClass
    public void beforeClass() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        //assertTrue("Failed before class", false);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        //assertTrue("Failed before method", false);
    }
    
    @BeforeMethod
    public void beforeMethod2() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
    }

    @Test
    public void testTestCasePassed() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);        
        assertTrue("Passed test case", true);
    }

    @Test(expectedExceptions = { AssertionError.class })
    public void testTestCaseFailed() throws Exception{
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        assertTrue("Failed test case", false);
    }

    @AfterTest
    public void afterTest(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);       
    }

    @AfterMethod
    public void afterMethod2(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
    }

    @AfterMethod
    public void afterMethod(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
    }

    @AfterClass 
    public void afterClass(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
    }

}
