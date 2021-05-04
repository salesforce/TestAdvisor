package com.salesforce.cqe.configuration;

/**
 * @author Yibing Tao
 * This interface defines how to load configuration from disk
 */
public interface IConfiguration {
    /**
     * Load static configration from disk
     */
    public void load();
}
