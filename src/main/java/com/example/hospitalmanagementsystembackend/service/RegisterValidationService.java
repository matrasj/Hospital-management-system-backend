package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.exception.auth.UsernameAlreadyExistsException;
import com.example.hospitalmanagementsystembackend.model.payload.auth.registration.RegistrationPayloadRequest;
import com.example.hospitalmanagementsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterValidationService {
    private static final String USERNAME_ALREADY_EXISTS_MESSAGE = "User with username: %s already exists";
    private final UserRepository userRepository;
    
    public void validateRegistrationRequest(RegistrationPayloadRequest request) {
        checkIfSameUserAlreadyExists(request.getUsername());
    }
    
    private void checkIfSameUserAlreadyExists(String username) {
        boolean userPresence = userRepository.findByUsername(username).isPresent();
        if (userPresence) {
            throw new UsernameAlreadyExistsException(String.format(USERNAME_ALREADY_EXISTS_MESSAGE, username));
        }
    }


}
