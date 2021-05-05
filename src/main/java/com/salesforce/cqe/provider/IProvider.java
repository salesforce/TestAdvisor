package com.salesforce.cqe.provider;

/**
 * @author Yibing Tao
 * This interface defines how to create and run test case 
 * from test provider
 */
public interface IProvider {
    public void create();
    public void run();
}
