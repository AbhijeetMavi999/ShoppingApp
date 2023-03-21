package com.customer.exception;

public class InputNotValidException extends RuntimeException {

    public InputNotValidException() {
    }

    public InputNotValidException(String message) {
        super(message);
    }
}
