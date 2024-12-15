package com.wizh.SalesApp.controller;

import com.wizh.SalesApp.dto.AuthenticationRequestDto;
import com.wizh.SalesApp.dto.AuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final com.wizh.SalesApp.service.AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refresh(final Authentication authentication) {
        return ResponseEntity.ok(authenticationService.refresh(authentication));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponseDto> logout(final Authentication authentication) {
        return ResponseEntity.ok(authenticationService.logout(authentication));
    }
}