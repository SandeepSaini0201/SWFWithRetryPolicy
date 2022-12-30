package com.crptm.ws;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldActivityImpl implements IHelloWorldActivity {

    private static boolean isExceptionExist = true;

    @Override
    public void sayGreetings() {
        if (isExceptionExist) {
            isExceptionExist = false;
            throw new ActivityFailureException("Say greeting activity failed...");
        }
        System.out.println("Hello World");
    }
}
