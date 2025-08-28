package com.example.demo.Exceptions;

public class DuplicateAccountTypeException extends RuntimeException {

    public DuplicateAccountTypeException(String message) {
        super(message);
    }

    public DuplicateAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAccountTypeException() {
        super("Account type already exists for this user.");
    }
}
