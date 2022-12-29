package com.crptm.ws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

@Activities(version = "2.1")
@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 300)
public interface IHelloWorldActivity {

    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 5, exceptionsToRetry = ActivityFailureException.class)
    String getGreetings();

    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 5, exceptionsToRetry = ActivityFailureException.class)
    void sayGreetings(final String greeting);
}
