package com.admin.exception;

public class InputNotValidException extends RuntimeException {

    public InputNotValidException() {
        super();
    }

    public InputNotValidException(String message) {
        super(message);
    }
}
