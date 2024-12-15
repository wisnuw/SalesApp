package com.wizh.SalesApp.mapper;

import com.wizh.SalesApp.dto.RegistrationRequestDto;
import com.wizh.SalesApp.dto.RegistrationResponseDto;
import com.wizh.SalesApp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User toEntity(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(final User user) {
        return new RegistrationResponseDto(user.getUsername());
    }

}