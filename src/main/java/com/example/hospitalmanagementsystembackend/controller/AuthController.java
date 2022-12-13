package com.example.hospitalmanagementsystembackend.controller;

import com.example.hospitalmanagementsystembackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.hospitalmanagementsystembackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadResponse;
import com.example.hospitalmanagementsystembackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationPayloadResponse> registerUser(@RequestBody @Valid RegistrationPayloadRequest request) {
        return ResponseEntity.status(CREATED)
                .body(authService.registerUser(request));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmToken(@RequestParam String token) {
        return ResponseEntity.status(OK)
                .body(authService.confirmAccount(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginPayloadResponse> login(@RequestBody @Valid LoginPayloadRequest request) {
        return ResponseEntity.status(OK)
                .body(authService.login(request));
    }

   

}
