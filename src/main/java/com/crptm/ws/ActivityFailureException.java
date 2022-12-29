package com.crptm.ws;

public class ActivityFailureException extends BaseException {

	private static final long serialVersionUID = -7501955439747524761L;

	public ActivityFailureException(String message) {
        super(message);
    }

    public ActivityFailureException(String message, int code) {
        super(message, code);
    }

    public ActivityFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
