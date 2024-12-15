package com.wizh.SalesApp.service;

import com.wizh.SalesApp.dto.AuthenticationRequestDto;
import com.wizh.SalesApp.dto.AuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {
        final var authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
//        final var authentication = authenticationManager.authenticate(authToken);

        final var token = jwtService.generateToken(request.username());
        return new AuthenticationResponseDto(token, request.username());
    }

    public AuthenticationResponseDto refresh(Authentication authentication) {
        final var user = userService.getUserByUsername(authentication.getName());

        if(user != null) {
            final var token = jwtService.generateToken(user.getUsername());
            return new AuthenticationResponseDto(token, user.getUsername());
        }

        return null;
    }

    public AuthenticationResponseDto logout(Authentication authentication) {
        final var user = userService.getUserByUsername(authentication.getName());
        if(user != null) {
            authentication.setAuthenticated(false);
            return new AuthenticationResponseDto(null, user.getUsername());
        }

        return null;
    }
}