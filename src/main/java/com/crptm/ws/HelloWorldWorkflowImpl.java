package com.crptm.ws;

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClock;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.amazonaws.services.simpleworkflow.flow.interceptors.AsyncRetryingExecutor;
import com.amazonaws.services.simpleworkflow.flow.interceptors.ExponentialRetryPolicy;

import java.util.ArrayList;
import java.util.List;

public class HelloWorldWorkflowImpl implements IHelloWorldWorkflow {

    private final IHelloWorldActivityClient helloWorldActivityClient = new IHelloWorldActivityClientImpl();

    @Override
    public void greet() {
        try {
            WorkflowClock clock = new DecisionContextProviderImpl().getDecisionContext().getWorkflowClock();
            ExponentialRetryPolicy retryPolicy = this.getExponentialRetryPolicy();
            final AsyncRetryingExecutor retryExecutor = new AsyncRetryingExecutor(retryPolicy, clock);
            retryExecutor.execute(this::executeActivityUpdatingInvocationHistory);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void executeActivityUpdatingInvocationHistory() {
        new TryCatchFinally() {

            @Override
            protected void doTry() {
                System.out.println("************ Trying ************");
                helloWorldActivityClient.sayGreetings();
            }

            @Override
            protected void doCatch(Throwable e) throws Throwable {
                System.out.println("************ Catch ************");
                throw e;
            }

            @Override
            protected void doFinally() {
                System.out.println("************ Finally ************");
            }
        };
    }

    private ExponentialRetryPolicy getExponentialRetryPolicy() {
        ExponentialRetryPolicy retryPolicy = new ExponentialRetryPolicy(1L);
        retryPolicy.setExceptionsToRetry(new ArrayList<>(List.of(ActivityFailureException.class)));
        retryPolicy.setMaximumRetryIntervalSeconds(60L);
        retryPolicy.setRetryExpirationIntervalSeconds(300L);
        retryPolicy.setMaximumAttempts(5);
        return retryPolicy;
    }
}
