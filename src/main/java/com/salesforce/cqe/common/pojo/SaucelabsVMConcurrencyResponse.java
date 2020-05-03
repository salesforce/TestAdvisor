package com.salesforce.cqe.common.pojo;

import lombok.Getter;
import lombok.Setter;


/*
class to map response from saucelabs concurrency api: "https://saucelabs.com/rest/v1.2/users/" + <saucelabs username> + "/concurrency";
example of response: {
    "timestamp": 1588253336.54877,
    "concurrency": {
        "organization": {
            "current": {
                "vms": 1,
                "rds": 0,
                "mac_vms": 0
            },
            "id": "74048c3e684d4a4ab628d496dde67d52",
            "allowed": {
                "vms": 15,
                "rds": 0,
                "mac_vms": 15
            }
        },
        "team": {}
    }
}
 */
@Getter
@Setter
public class SaucelabsVMConcurrencyResponse {
    public double timestamp;
    public Concurrency concurrency;

    @Getter
    @Setter
    public static class Concurrency{
        private Organization organization;
        private Team team;
    }

    @Getter
    @Setter
    public static class Team{

    }

    @Getter
    @Setter
    public static class Organization{
        private Current current;
        private Allowed allowed;
        private String id;
    }

    @Getter
    @Setter
    public static class Current{
        private int vms;
        private int rds;
        private int mac_vms;
    }

    @Getter
    @Setter
    public static class Allowed{
        private int vms;
        private int rds;
        private int mac_vms;
    }
}
