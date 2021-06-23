package com.salesforce.cqe.test.listener;

import com.salesforce.cqe.provider.listener.TestListener;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class TestProviderListener {
    

    @BeforeClass
    public void setupClass(){
        System.out.println("BeforeClass setup...");
    }

    @BeforeMethod
    public void setupMethod(){
        System.out.println("BeforeMethod setup...");
    }
    
    @BeforeTest
    public void setup(){
        System.out.println("BeforeTest setup...");
    }

    @Test
    public void testListener(){
        System.out.println("Test case...");
    }
}
