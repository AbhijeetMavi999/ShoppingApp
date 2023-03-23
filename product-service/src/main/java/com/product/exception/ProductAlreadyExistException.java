package com.product.exception;

public class ProductAlreadyExistException extends RuntimeException {

    public ProductAlreadyExistException() {
    }

    public ProductAlreadyExistException(String message) {
        super(message);
    }
}
