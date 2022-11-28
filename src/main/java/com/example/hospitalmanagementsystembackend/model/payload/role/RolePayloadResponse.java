package com.example.hospitalmanagementsystembackend.model.payload.role;

import com.example.hospitalmanagementsystembackend.model.payload.authority.AuthorityPayloadResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RolePayloadResponse {
    private String name;
    private Set<AuthorityPayloadResponse> authorities = new HashSet<>();
}
