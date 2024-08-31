package com.bhavesh.exception;

public class BookAlreadyBorrowdException extends RuntimeException {
    public BookAlreadyBorrowdException(String message) {
        super(message);
    }
}
