package com.wizh.SalesApp.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CheckoutRequestDto {
    private List<CheckoutItemDto> items;
}
