package com.salesforce.cte.test.listener;

import static org.junit.Assert.assertTrue;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

public class TestJUnitListener {
    
    @Test
    public void testPass(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        assertTrue("Passing test.",true);
    }

    @Test
    public void testFail(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        assertTrue("Passing test.",false);
    }

    @Ignore
    @Test
    public void testIgnore(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        assertTrue("Passing test.",false);
    }

    @Test
    public void testAssume(){
        String methodName = new Object() {}
                                .getClass()
                                .getEnclosingMethod()
                                .getName();
        System.out.println(methodName);
        Assume.assumeTrue("Assume test.",false);
    }
}
