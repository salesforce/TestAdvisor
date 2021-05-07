package com.salesforce.cqe.provider;

/**
 * This interface defines how to create and run test case from test provider.
 * 
 * @author Yibing Tao
 */
public interface IProvider {
    public void create();
    public void run();
}
