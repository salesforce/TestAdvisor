package com.salesforce.cqe.manager;

import com.salesforce.cqe.configuration.ConfigurationBase;
import com.salesforce.cqe.configuration.IConfiguration;
import com.salesforce.cqe.context.ExecutionContext;
import com.salesforce.cqe.context.IExecutionContext;
import com.salesforce.cqe.provider.IProvider;
import com.salesforce.cqe.provider.ProviderBase;

/**
 * @author Yibing Tao
 * Main entry point for Drillbit test execution
 */
public class DrillbitTestExecutionManager {

    private ConfigurationBase configuration;
    private IProvider provider;
    private ExecutionContext context;

    private void loadConfiguration(){
        configuration = new ConfigurationBase();
        configuration.load();
    }

    public DrillbitTestExecutionManager(){
        loadConfiguration();
        createExecutionContext(configuration);
        createProvider();
    }

    private void createExecutionContext(ConfigurationBase config){
        context = new ExecutionContext();
        context.build(config);
    }

    private void createProvider(){
        provider = new ProviderBase();

    }

    public void run(){
        provider.run();
    }
    
}
