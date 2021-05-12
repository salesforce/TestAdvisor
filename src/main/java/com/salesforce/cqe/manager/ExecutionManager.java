package com.salesforce.cqe.manager;

import com.salesforce.cqe.configuration.Configuration;
import com.salesforce.cqe.context.ExecutionContext;

import org.testng.IExecutionListener;

/**
 * @author Yibing Tao
 * Main entry point for this test framework
 * It will load configuration, create context and provider 
 * to run test cases.
 */
public class ExecutionManager implements IExecutionListener{

    private Configuration configuration;
    
    //TODO: How about parallel exectuion?
    private static ExecutionContext context = new ExecutionContext();
    public static ExecutionContext getExecutionContext(){
        return context;
    }

    private void loadConfiguration(){
        configuration = Configuration.load();
    }

    private void createExecutionContext(Configuration config){
        context.build(config);
    }

    @Override
    public void onExecutionStart() {
        loadConfiguration();
        createExecutionContext(configuration);
    }

    
}
