package com.salesforce.cqe.provider;

/**
 * Base class for test provider. Test provider will actually run the test cases.
 * Please derive from this class to create provide to run different type of test 
 * case, such as TestNG or JUnit.
 * 
 * @author Yibing Tao
 */
public class ProviderBase implements IProvider{

    @Override
    public void create() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
        
    }
    
}
