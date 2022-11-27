package com.example.hospitalmanagementsystembackend.exception.auth;


public class ConfirmationTokenNotExistsException extends RuntimeException {
    public ConfirmationTokenNotExistsException(String message) {
        super(message);
    }
}
