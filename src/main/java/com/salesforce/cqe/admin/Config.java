package com.salesforce.cqe.admin;

class Config {

    String getOS() {
        return System.getProperty("os.name");
    }

    String getUserDirectory() {
        return System.getProperty("user.dir");
    }
    
}
