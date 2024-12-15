package com.wizh.SalesApp.service;

import com.wizh.SalesApp.entity.User;
import com.wizh.SalesApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(GONE, "The user account has been deleted or inactivated"));
    }
}