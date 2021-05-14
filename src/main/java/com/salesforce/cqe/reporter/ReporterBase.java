package com.salesforce.cqe.reporter;

/**
 * Base class for all classes implementing {@link IReporter}.
 * 
 * A reporter decides how to save data. Every implementation of either the {@link IEventListener}
 * or the {@link ITestListener} interface will call reporter classes to write logs to disk.
 *  
 * Please derive from this class to create specific type of reporter, such as XML, HTML, JSON or flat file.
 * 
 * @author Yibing Tao
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
