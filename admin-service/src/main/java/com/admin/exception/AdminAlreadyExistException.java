package com.admin.exception;

public class AdminAlreadyExistException extends RuntimeException {

    public AdminAlreadyExistException() {
        super();
    }

    public AdminAlreadyExistException(String message) {
        super(message);
    }
}
