package com.salesforce.cte.test.listener;

import com.salesforce.cte.listener.junit.TestListenerJUnit4;

import org.junit.runner.JUnitCore;
import org.testng.annotations.Test;

public class TestJUnitListenerRunner {
    
    @Test
    public void testJUnitListener(){
        JUnitCore runner = new JUnitCore();
        
        runner.addListener(new TestListenerJUnit4());
        runner.run(TestJUnitListener.class);
    }
}
