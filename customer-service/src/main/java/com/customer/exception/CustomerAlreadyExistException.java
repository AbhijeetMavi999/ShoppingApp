package com.customer.exception;

public class CustomerAlreadyExistException extends RuntimeException {

    public CustomerAlreadyExistException() {
        super();
    }

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
