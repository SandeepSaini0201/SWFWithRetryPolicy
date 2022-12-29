package com.crptm.ws;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.common.FlowConstants;
import com.amazonaws.services.simpleworkflow.model.ActivityType;

public class CronWithRetryWorkflowOptions {

    private ActivityType activity;

    private Object[] activityArguments;

    private String cronExpression;

    private String timeZone;
    
    private int continueAsNewAfterSeconds;

    private List<String> exceptionsToRetry;

    private List<String> exceptionsToExclude;

    private long initialRetryIntervalSeconds = 60;

    private long maximumRetryIntervalSeconds = 3600;

    private long retryExpirationIntervalSeconds = FlowConstants.NONE;

    private double backoffCoefficient = 2.0;

    private int maximumAttempts = FlowConstants.NONE;

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public Object[] getActivityArguments() {
        return activityArguments;
    }

    public void setActivityArguments(Object[] activityArguments) {
        this.activityArguments = activityArguments;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getContinueAsNewAfterSeconds() {
        return continueAsNewAfterSeconds;
    }

    public void setContinueAsNewAfterSeconds(int continueAsNewAfterSeconds) {
        this.continueAsNewAfterSeconds = continueAsNewAfterSeconds;
    }

    public List<String> getExceptionsToRetry() {
        return exceptionsToRetry;
    }

    public void setExceptionsToRetry(List<String> exceptionsToRetry) {
        this.exceptionsToRetry = exceptionsToRetry;
    }

    public List<String> getExceptionsToExclude() {
        return exceptionsToExclude;
    }

    public void setExceptionsToExclude(List<String> exceptionsToExclude) {
        this.exceptionsToExclude = exceptionsToExclude;
    }

    public long getInitialRetryIntervalSeconds() {
        return initialRetryIntervalSeconds;
    }

    public void setInitialRetryIntervalSeconds(long initialRetryIntervalSeconds) {
        this.initialRetryIntervalSeconds = initialRetryIntervalSeconds;
    }

    public long getMaximumRetryIntervalSeconds() {
        return maximumRetryIntervalSeconds;
    }

    public void setMaximumRetryIntervalSeconds(long maximumRetryIntervalSeconds) {
        this.maximumRetryIntervalSeconds = maximumRetryIntervalSeconds;
    }

    public long getRetryExpirationIntervalSeconds() {
        return retryExpirationIntervalSeconds;
    }

    public void setRetryExpirationIntervalSeconds(long retryExpirationIntervalSeconds) {
        this.retryExpirationIntervalSeconds = retryExpirationIntervalSeconds;
    }

    public double getBackoffCoefficient() {
        return backoffCoefficient;
    }

    public void setBackoffCoefficient(double backoffCoefficient) {
        this.backoffCoefficient = backoffCoefficient;
    }

    public int getMaximumAttempts() {
        return maximumAttempts;
    }

    public void setMaximumAttempts(int maximumAttempts) {
        this.maximumAttempts = maximumAttempts;
    }

}
