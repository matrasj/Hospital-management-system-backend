package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.mapper.RolePayloadResponseMapper;
import com.example.hospitalmanagementsystembackend.model.entity.Role;
import com.example.hospitalmanagementsystembackend.model.entity.User;
import com.example.hospitalmanagementsystembackend.model.payload.role.RolePayloadResponse;
import com.example.hospitalmanagementsystembackend.repository.RoleRepository;
import com.example.hospitalmanagementsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Set<RolePayloadResponse> findRolesAssignedToAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account with username: %s does not exists", username)));

        Set<Role> roles = user.getRoles();

        return roles
                .stream()
                .map(RolePayloadResponseMapper::mapToRolePayloadResponse)
                .collect(Collectors.toSet());

    }
}
