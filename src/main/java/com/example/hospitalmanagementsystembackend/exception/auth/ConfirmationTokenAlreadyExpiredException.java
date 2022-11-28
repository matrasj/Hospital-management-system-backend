package com.example.hospitalmanagementsystembackend.exception.auth;

public class ConfirmationTokenAlreadyExpiredException extends RuntimeException {
    public ConfirmationTokenAlreadyExpiredException(String message) {
        super(message);
    }
}
