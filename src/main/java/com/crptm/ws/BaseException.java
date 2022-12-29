package com.crptm.ws;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -7248495056244730402L;
	
	private int code;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, int code) {
        super(message);
        this.setCode(code);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
