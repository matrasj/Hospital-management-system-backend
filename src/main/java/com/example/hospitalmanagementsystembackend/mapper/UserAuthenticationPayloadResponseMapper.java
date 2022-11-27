package com.example.hospitalmanagementsystembackend.mapper;

import com.example.hospitalmanagementsystembackend.model.entity.User;
import com.example.hospitalmanagementsystembackend.model.payload.auth.shared.UserAuthenticationPayloadResponse;

public class UserAuthenticationPayloadResponseMapper {
    public static UserAuthenticationPayloadResponse mapToUserAuthenticationPayloadResponse(User user) {
        return UserAuthenticationPayloadResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
