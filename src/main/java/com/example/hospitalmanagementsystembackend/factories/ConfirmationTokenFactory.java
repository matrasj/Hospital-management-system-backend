package com.example.hospitalmanagementsystembackend.factories;

import com.example.hospitalmanagementsystembackend.model.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.UUID;


public class ConfirmationTokenFactory {
    public static ConfirmationToken generateConfirmationToken() {
        return ConfirmationToken.builder()
                .token(generateRandomUUID())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
    }

    private static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
