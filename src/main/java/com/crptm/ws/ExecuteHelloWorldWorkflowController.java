package com.crptm.ws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExecuteHelloWorldWorkflowController {

    @GetMapping(value = "/execute/workflow")
    public ResponseEntity<String> executeHelloWorldWorkflow() {
        try {
            AmazonSimpleWorkflow amazonSimpleWorkflow = AmazonSimpleWorkflowClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider("default"))
                    .withRegion(Regions.US_EAST_1).build();

            IHelloWorldWorkflowClientExternalFactory factory = new IHelloWorldWorkflowClientExternalFactoryImpl(amazonSimpleWorkflow, "HelloWorld");
            IHelloWorldWorkflowClientExternal greeter = factory.getClient("CRPTM_USER_ID");
            greeter.greet();

            return new ResponseEntity<>("SUCCESS", new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
