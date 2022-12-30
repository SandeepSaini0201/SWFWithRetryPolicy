package com.crptm.ws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.spring.SpringActivityWorker;

public class HelloWorldActivityHost {

    public static void initializeActivities() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        AmazonSimpleWorkflow amazonSimpleWorkflow = AmazonSimpleWorkflowClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider("default"))
                .withRegion(Regions.US_EAST_1)
                .build();

        SpringActivityWorker springActivityWorker = new SpringActivityWorker(amazonSimpleWorkflow, "HelloWorld", "HelloWorldTask");
        springActivityWorker.addActivitiesImplementation(new HelloWorldActivityImpl());
        springActivityWorker.start();
    }
}
