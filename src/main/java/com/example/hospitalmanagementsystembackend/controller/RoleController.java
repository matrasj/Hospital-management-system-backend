package com.example.hospitalmanagementsystembackend.controller;

import com.example.hospitalmanagementsystembackend.model.payload.role.RolePayloadResponse;
import com.example.hospitalmanagementsystembackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {
    private final RoleService roleService;


    @GetMapping("/user/{username}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Set<RolePayloadResponse>> getRoleAssignedToAccount(@PathVariable String username) {
        return ResponseEntity.status(OK)
                .body(roleService.findRolesAssignedToAccount(username));
    }
}
