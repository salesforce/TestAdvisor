package com.salesforce.cqe.manager;

import com.salesforce.cqe.configuration.Configuration;
import com.salesforce.cqe.context.ExecutionContext;
import com.salesforce.cqe.provider.IProvider;
import com.salesforce.cqe.provider.ProviderBase;

/**
 * @author Yibing Tao
 * Main entry point for this test framework
 * It will load configuration, create context and provider 
 * to run test cases.
 */
public class DrillbitTestExecutionManager {

    private Configuration configuration;
    private IProvider provider;
    private ExecutionContext context;

    private void loadConfiguration(){
        configuration =  Configuration.load();
    }

    public DrillbitTestExecutionManager(){
        loadConfiguration();
        createExecutionContext(configuration);
        createProvider();
    }

    private void createExecutionContext(Configuration config){
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
