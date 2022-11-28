package com.example.hospitalmanagementsystembackend.mapper;

import com.example.hospitalmanagementsystembackend.model.entity.Role;
import com.example.hospitalmanagementsystembackend.model.payload.role.RolePayloadResponse;

import java.util.stream.Collectors;

public class RolePayloadResponseMapper {
    public static RolePayloadResponse mapToRolePayloadResponse(Role role) {
        return new RolePayloadResponse(
                role.getName(),
                role.getAuthorities()
                        .stream()
                        .map(AuthorityPayloadResponseMapper::mapToAuthorityPayloadResponse)
                        .collect(Collectors.toSet())
        );
    }
}
