package com.wizh.SalesApp.service;

import com.wizh.SalesApp.entity.User;
import com.wizh.SalesApp.exception.ValidationException;
import com.wizh.SalesApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(User user) {
        final var errors = new HashMap<String, String>();

        if (userRepository.existsByUsername(user.getUsername())) {
            errors.put("username", "Username [%s] is already taken".formatted(user.getUsername()));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(CONFLICT, errors);
        }

        user.setAdmin(false);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

}