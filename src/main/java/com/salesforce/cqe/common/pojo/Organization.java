package com.salesforce.cqe.common.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization {
    public Current current;
    public Allowed allowed;
    public String id;


    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
