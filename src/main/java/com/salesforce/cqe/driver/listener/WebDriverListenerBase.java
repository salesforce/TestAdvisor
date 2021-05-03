package com.salesforce.cqe.driver.listener;

import com.salesforce.selenium.support.event.AbstractWebDriverEventListener;

/**
 * @author Yibing Tao
 * Base class for all web driver listener
 */
public class WebDriverListenerBase extends AbstractWebDriverEventListener{

    @Override
    public void closeListener() {
        throw new UnsupportedOperationException();
        
    }
    
}
