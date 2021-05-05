package com.salesforce.cqe.reporter;

/**
 * @author Yibing Tao
 * Base class for all Reporters. Reporter decides how to save data.
 * The listener will call reporter to write logs to disk.  
 * Please derive from this class to create specific type of reporter,
 * such as xml reporter, html reporter or flat file reporter.
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
