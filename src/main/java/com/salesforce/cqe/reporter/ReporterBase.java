package com.salesforce.cqe.reporter;

/**
 * Base class for all classes implementing {@link IReporter}.
 * 
 * A reporter decides how to save data. Every implementation of the {@link com.salesforce.cqe.driver.listener.IEventListener}
 * interface will call reporter classes to write logs to disk.
 *  
 * Please derive from this class to create specific type of reporter, such as XML, HTML, JSON or flat file.
 * 
 * @author Yibing Tao
 */
public class ReporterBase implements IReporter {

    public ReporterBase(String outputFolder){

    }
    
    @Override
    public void create() {
        //not implemented yet
        
    }

    @Override
    public void log() {
        //not implemented yet
    }
    
}
