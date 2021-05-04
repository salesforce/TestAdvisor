package com.salesforce.cqe.context;

import com.salesforce.cqe.configuration.ConfigurationBase;

/**
 * @author Yibing Tao
 * This interface defines how to create a new execution context
 */

public interface IExecutionContext{
    /**
     * 
     * @param config context configruation
     * @return intance of newly created execution context
     */
    public ExecutionContext build(ConfigurationBase config);
}