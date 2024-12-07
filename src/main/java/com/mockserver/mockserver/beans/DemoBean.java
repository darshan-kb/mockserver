package com.mockserver.mockserver.beans;

import org.springframework.stereotype.Component;

@Component
public class DemoBean {

    public DemoBean(){
        System.out.println("Hello world from DemoBean");
    }
}
