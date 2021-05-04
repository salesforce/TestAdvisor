package com.salesforce.cqe.reporter;

/**
 * @author Yibing Tao
 * Base class for all Reporters
 */
public class ReporterBase implements IReporter {

    @Override
    public void create() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void log() {
        throw new UnsupportedOperationException();
        
    }
    
}
