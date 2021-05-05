package com.salesforce.cqe.reporter;

/**
 * @author Yibing Tao
 * This interface defines how to create and write logs
 */
public interface IReporter {
    public void create();
    public void log();
}
