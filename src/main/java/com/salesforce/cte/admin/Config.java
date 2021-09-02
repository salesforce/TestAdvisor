package com.salesforce.cte.admin;

public class Config {

    public String getOS() {
        return System.getProperty("os.name");
    }

    public String getUserDirectory() {
        return System.getProperty("user.dir");
    }
    
}
