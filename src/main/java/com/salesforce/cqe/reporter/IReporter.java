package com.salesforce.cqe.reporter;

/**
 * This interface defines how to create and write logs.
 * 
 * @author Yibing Tao
 */
public interface IReporter {
    public void create();
    public void log();
}
