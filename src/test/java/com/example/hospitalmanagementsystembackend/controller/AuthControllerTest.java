package com.example.hospitalmanagementsystembackend.controller;

import com.example.hospitalmanagementsystembackend.factories.ConfirmationTokenFactory;
import com.example.hospitalmanagementsystembackend.jwt.JwtTokenProvider;
import com.example.hospitalmanagementsystembackend.model.entity.ConfirmationToken;
import com.example.hospitalmanagementsystembackend.model.entity.User;
import com.example.hospitalmanagementsystembackend.model.payload.UserPrincipal;
import com.example.hospitalmanagementsystembackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.hospitalmanagementsystembackend.repository.ConfirmationTokenRepository;
import com.example.hospitalmanagementsystembackend.repository.UserRepository;
import com.example.hospitalmanagementsystembackend.service.AuthService;
import com.example.hospitalmanagementsystembackend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @MockBean
    AuthService authService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    UserDetailsServiceImpl userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        reset(userService);
    }

    @Test
    void registerUser() throws Exception {
        RegistrationPayloadRequest registrationPayloadRequest = buildRegistrationRequest();
        assertAll("Registration payloaf request failed",
            () -> assertEquals("username", registrationPayloadRequest.getUsername(), "Username failed"),
            () -> assertEquals("password", registrationPayloadRequest.getPassword(), "Password failed")
        );

        given(userRepository.save(any())).willReturn(any());
        String registerRequestAsString = objectMapper.writeValueAsString(registrationPayloadRequest);

        mockMvc.perform(
                post("/api/v1/auth/registration")
                        .contentType(APPLICATION_JSON)
                        .content(registerRequestAsString)
                        .with(csrf())
        ).andExpect(status().isCreated());
    }

    private RegistrationPayloadRequest buildRegistrationRequest() {
        return RegistrationPayloadRequest.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    void confirmToken() throws Exception {
        ConfirmationToken confirmationToken = ConfirmationTokenFactory.generateConfirmationToken();
        given(confirmationTokenRepository.findByToken(confirmationToken.getToken()))
                .willReturn(Optional.of(confirmationToken));

        mockMvc.perform(
                get("/api/v1/auth/confirmation")
                        .contentType(APPLICATION_JSON)
                        .param("token", confirmationToken.getToken())
                        .with(csrf())

        ).andExpect(status().isOk());

    }

    @Test
    void login() throws Exception {
        User user = buildUser();
        assertAll("User fields test",
                () -> assertEquals("Jon", user.getFirstName(), "First name failed"),
                () -> assertEquals("Snow", user.getLastName(), "Last name failed"),
                () -> assertEquals("jonsnow", user.getUsername(), "Username failed"),
                () -> assertEquals("jonsnow", user.getPassword(), "Password filed"),
                () -> assertEquals("jon.snow@gmail.com", user.getEmail(), "Email failed")
        );


        UserPrincipal userPrincipal = new UserPrincipal(user);
        LoginPayloadRequest buildRequest = LoginPayloadRequest.builder()
                .username(userPrincipal.getUsername())
                .password(userPrincipal.getPassword())
                .build();

        String loginPayloadRequestAsString = objectMapper.writeValueAsString(buildRequest);

        given(authenticationManager.authenticate(any()))
                .willReturn(new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword()));


        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginPayloadRequestAsString)
                        .with(csrf())

        ).andExpect(status().isOk());
    }

    private User buildUser() {
        return User.builder()
                .id(1L)
                .firstName("Jon")
                .lastName("Snow")
                .username("jonsnow")
                .password("jonsnow")
                .email("jon.snow@gmail.com")
                .build();
    }
}