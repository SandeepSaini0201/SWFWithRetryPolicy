package com.crptm.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.DynamicActivitiesClient;
import com.amazonaws.services.simpleworkflow.flow.DynamicActivitiesClientImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClock;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.amazonaws.services.simpleworkflow.flow.interceptors.AsyncRetryingExecutor;
import com.amazonaws.services.simpleworkflow.flow.interceptors.AsyncRunnable;
import com.amazonaws.services.simpleworkflow.flow.interceptors.AsyncScheduledExecutor;
import com.amazonaws.services.simpleworkflow.flow.interceptors.ExponentialRetryPolicy;
import com.amazonaws.services.simpleworkflow.flow.spring.CronInvocationSchedule;

public class CronWithRetryWorkflowImpl implements CronWithRetryWorkflow {

	private static final int SECOND = 1000;

//	private final DynamicActivitiesClient activities;

//	private final CronWithRetryWorkflowSelfClient selfClient;

	private final StringBuilder invocationHistory = new StringBuilder();

	private TimeZone tz;

//	public CronWithRetryWorkflowImpl() {
//		this(new DynamicActivitiesClientImpl(), new CronWithRetryWorkflowSelfClientImpl());
//	}

//	public CronWithRetryWorkflowImpl(DynamicActivitiesClient activities, CronWithRetryWorkflowSelfClient selfClient) {
//		this.activities = activities;
//		this.selfClient = selfClient;
//	}

	@Override
	public void startCron(final CronWithRetryWorkflowOptions options) {
		try {
			WorkflowClock clock = new DecisionContextProviderImpl().getDecisionContext().getWorkflowClock();
			Date expiration = new Date(clock.currentTimeMillis() + options.getContinueAsNewAfterSeconds() * SECOND);
			tz = TimeZone.getTimeZone("GMT+05:30");

			CronInvocationSchedule cronSchedule = new CronInvocationSchedule("*/10 * * * * *", expiration, tz);
			AsyncScheduledExecutor scheduledExecutor = new AsyncScheduledExecutor(cronSchedule, clock);
			ExponentialRetryPolicy retryPolicy = createRetryPolicyFromOptions(options);
			final AsyncRetryingExecutor retryExecutor = new AsyncRetryingExecutor(retryPolicy, clock);

			scheduledExecutor.execute(new AsyncRunnable() {

				@Override
				public void run() throws Throwable {
					retryExecutor.execute(new AsyncRunnable() {

						@Override
						public void run() throws Throwable {
							executeActivityUpdatingInvocationHistory(options);
						}
					});
				}
			});
//			selfClient.startCron(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getInvocationHistory() {
		return invocationHistory.toString();
	}

	private void appendToInvocationHistory(String entry) {
		if (invocationHistory.length() > 0) {
			invocationHistory.append('\n');
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(tz);
		WorkflowClock clock = new DecisionContextProviderImpl().getDecisionContext().getWorkflowClock();
		invocationHistory.append(dateFormat.format(new Date(clock.currentTimeMillis())));
		invocationHistory.append(" ");
		invocationHistory.append(entry);
	}

	private void executeActivityUpdatingInvocationHistory(final CronWithRetryWorkflowOptions options) {
		new TryCatchFinally() {

			boolean failed;

			@Override
			protected void doTry() throws Throwable {
				appendToInvocationHistory("starting");
//				activities.scheduleActivity(options.getActivity(), new Object[] {"parameter1"}, null, Void.class);
			}

			@Override
			protected void doCatch(Throwable e) throws Throwable {
				failed = true;
				appendToInvocationHistory("failure:" + e.getMessage());
				throw e;
			}

			@Override
			protected void doFinally() throws Throwable {
				if (!failed) {
					appendToInvocationHistory("completed");
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	private ExponentialRetryPolicy createRetryPolicyFromOptions(CronWithRetryWorkflowOptions options) {
		ExponentialRetryPolicy retryPolicy = new ExponentialRetryPolicy(options.getInitialRetryIntervalSeconds());
		retryPolicy.setBackoffCoefficient(options.getBackoffCoefficient());
		retryPolicy.setExceptionsToRetry(new ArrayList<Class<? extends Throwable>>(List.of(ActivityFailureException.class)));
		retryPolicy.setMaximumAttempts(options.getMaximumAttempts());
		retryPolicy.setMaximumRetryIntervalSeconds(options.getMaximumRetryIntervalSeconds());
		retryPolicy.setRetryExpirationIntervalSeconds(options.getRetryExpirationIntervalSeconds());
		retryPolicy.setMaximumAttempts(5);
		return retryPolicy;
	}
}
