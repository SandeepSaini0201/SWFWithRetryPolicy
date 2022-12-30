package com.crptm.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        initializeHosts();
        SpringApplication.run(WsApplication.class, args);
    }

    private static void initializeHosts() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        HelloWorldActivityHost.initializeActivities();
        HelloWorldWorkflowHost.initializeWorkflows();
    }
}
