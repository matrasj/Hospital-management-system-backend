package com.example.hospitalmanagementsystembackend.exception.auth;

public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException {
    public ConfirmationTokenAlreadyConfirmedException(String message) {
        super(message);
    }
}
