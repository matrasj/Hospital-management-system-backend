package com.example.hospitalmanagementsystembackend.model.payload.auth.registration;

import com.example.hospitalmanagementsystembackend.model.payload.auth.shared.ConfirmationTokenPayloadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationPayloadResponse {
    private String username;
    private String email;
    private ConfirmationTokenPayloadResponse confirmationToken;
}
