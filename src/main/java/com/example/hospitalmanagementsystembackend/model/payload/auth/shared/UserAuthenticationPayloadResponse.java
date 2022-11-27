package com.example.hospitalmanagementsystembackend.model.payload.auth.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthenticationPayloadResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
