package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.exception.auth.ConfirmationTokenAlreadyConfirmedException;
import com.example.hospitalmanagementsystembackend.exception.auth.ConfirmationTokenAlreadyExpiredException;
import com.example.hospitalmanagementsystembackend.exception.auth.ConfirmationTokenNotExistsException;
import com.example.hospitalmanagementsystembackend.factories.ConfirmationTokenFactory;
import com.example.hospitalmanagementsystembackend.jwt.JwtTokenProvider;
import com.example.hospitalmanagementsystembackend.mapper.ConfirmationTokenPayloadResponseMapper;
import com.example.hospitalmanagementsystembackend.mapper.UserAuthenticationPayloadResponseMapper;
import com.example.hospitalmanagementsystembackend.model.entity.ConfirmationToken;
import com.example.hospitalmanagementsystembackend.model.entity.User;
import com.example.hospitalmanagementsystembackend.model.payload.auth.login.LoginPayloadRequest;
import com.example.hospitalmanagementsystembackend.model.payload.auth.login.LoginPayloadResponse;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadResponse;
import com.example.hospitalmanagementsystembackend.repository.ConfirmationTokenRepository;
import com.example.hospitalmanagementsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final boolean DISABLED_ACCOUNT = false;
    private static final String CONFIRMATION_TOKEN_NOT_FOUND_MESSAGE = "Token %s doest not exists";
    private static final String CONFIRMATION_TOKEN_ALREADY_CONFIRMED_MESSAGE = "Token %s has been already confirmed";
    private static final String CONFIRMATION_TOKEN_ALREADY_EXPIRED_MESSAGE = "Token %s already expired";
    private static final boolean ENABLED_ACCOUNT = true;
    private static final String SUCCESSFULLY_CONFIRMED_ACCOUNT_MESSAGE = "Successfully confirmed account";
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Not found user with username: %s";
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final RegisterValidationService registerValidationService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    public RegistrationPayloadResponse registerUser(RegistrationPayloadRequest request) {
        registerValidationService.validateRegistrationRequest(request);

        ConfirmationToken confirmationToken
                = ConfirmationTokenFactory.generateConfirmationToken();

        User user = buildUserFromRequest(request, confirmationToken);
        confirmationToken.setUser(user);

        userRepository.save(user);

        return buildRegistrationResponse(user, confirmationToken);

    }
    private User buildUserFromRequest(RegistrationPayloadRequest request, ConfirmationToken confirmationToken) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .tokens(Set.of(confirmationToken))
                .enabled(DISABLED_ACCOUNT)
                .build();
    }

    private RegistrationPayloadResponse buildRegistrationResponse(User user, ConfirmationToken confirmationToken) {
        return RegistrationPayloadResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .confirmationToken(
                        ConfirmationTokenPayloadResponseMapper.mapToConfirmationTokenPayloadResponse(confirmationToken)
                ).build();
    }


    @Transactional
    public String confirmAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotExistsException(String.format(CONFIRMATION_TOKEN_NOT_FOUND_MESSAGE, token)));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyConfirmedException(String.format(CONFIRMATION_TOKEN_ALREADY_CONFIRMED_MESSAGE, token));
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenAlreadyExpiredException(String.format(CONFIRMATION_TOKEN_ALREADY_EXPIRED_MESSAGE, token));
        }

        confirmationToken.getUser().setEnabled(ENABLED_ACCOUNT);
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        return SUCCESSFULLY_CONFIRMED_ACCOUNT_MESSAGE;
    }

    public LoginPayloadResponse login(LoginPayloadRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, request.getUsername())));

        String generatedJwtToken = jwtTokenProvider.generateJwtToken(authentication);
        return LoginPayloadResponse.builder()
                .jwtToken(generatedJwtToken)
                .user(UserAuthenticationPayloadResponseMapper.mapToUserAuthenticationPayloadResponse(user))
                .build();
    }
}
