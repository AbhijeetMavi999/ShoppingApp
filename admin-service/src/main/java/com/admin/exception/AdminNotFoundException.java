package com.admin.exception;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {
        super();
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}
