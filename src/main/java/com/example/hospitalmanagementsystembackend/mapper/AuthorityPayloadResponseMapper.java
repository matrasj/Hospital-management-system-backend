package com.example.hospitalmanagementsystembackend.mapper;

import com.example.hospitalmanagementsystembackend.model.entity.Authority;
import com.example.hospitalmanagementsystembackend.model.payload.authority.AuthorityPayloadResponse;

public class AuthorityPayloadResponseMapper {
    public static AuthorityPayloadResponse mapToAuthorityPayloadResponse(Authority authority) {
        return new AuthorityPayloadResponse(authority.getPermission());
    }
}
