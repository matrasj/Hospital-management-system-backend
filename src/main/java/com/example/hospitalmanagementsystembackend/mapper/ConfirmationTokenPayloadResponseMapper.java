package com.example.hospitalmanagementsystembackend.mapper;

import com.example.hospitalmanagementsystembackend.model.entity.ConfirmationToken;
import com.example.hospitalmanagementsystembackend.model.payload.auth.shared.ConfirmationTokenPayloadResponse;

public class ConfirmationTokenPayloadResponseMapper {
    public static ConfirmationTokenPayloadResponse mapToConfirmationTokenPayloadResponse(ConfirmationToken confirmationToken) {
        return ConfirmationTokenPayloadResponse.builder()
                .token(confirmationToken.getToken())
                .createdAt(confirmationToken.getCreatedAt())
                .expiresAt(confirmationToken.getExpiresAt())
                .confirmedAt(confirmationToken.getConfirmedAt())
                .build();
    }
}
