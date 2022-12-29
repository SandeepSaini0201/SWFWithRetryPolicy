package com.crptm.ws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.model.ActivityType;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
					.withCredentials(new ProfileCredentialsProvider("default")).withRegion(Regions.US_EAST_1).build();

			ActivityType activityTYpe = new ActivityType();
			activityTYpe.setName("IHelloWorldActivity.sayGreetings");
			activityTYpe.setVersion("2.1");

			CronWithRetryWorkflowOptions cronOptions = new CronWithRetryWorkflowOptions();
			cronOptions.setContinueAsNewAfterSeconds(30);
			cronOptions.setActivity(activityTYpe);
			cronOptions.setInitialRetryIntervalSeconds(1);
			cronOptions.setMaximumRetryIntervalSeconds(60);
			cronOptions.setRetryExpirationIntervalSeconds(300);
			List<String> exceptionsToRetry = new ArrayList<String>();
			exceptionsToRetry.add("ActivityFailureException");
			cronOptions.setExceptionsToRetry(exceptionsToRetry);

//			CronWithRetryWorkflowClientExternalFactory factory = new CronWithRetryWorkflowClientExternalFactoryImpl(amazonSimpleWorkflow, "LuckyMe");
//			CronWithRetryWorkflowClientExternal cronretry = factory.getClient("HelloMyFriend");
//			cronretry.startCron(cronOptions);

			return new ResponseEntity<>("SUCCESS", new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("ERROR", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
