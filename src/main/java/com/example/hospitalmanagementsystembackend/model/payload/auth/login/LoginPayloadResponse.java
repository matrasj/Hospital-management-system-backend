package com.example.hospitalmanagementsystembackend.model.payload.auth.login;

import com.example.hospitalmanagementsystembackend.model.payload.auth.shared.UserAuthenticationPayloadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginPayloadResponse {
    private String jwtToken;
    private UserAuthenticationPayloadResponse user;
}
