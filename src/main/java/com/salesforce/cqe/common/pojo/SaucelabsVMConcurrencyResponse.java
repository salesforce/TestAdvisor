package com.salesforce.cqe.common.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaucelabsVMConcurrencyResponse {
    public double timestamp;
    public Concurrency concurrency;
}



