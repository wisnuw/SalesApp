package com.wizh.SalesApp.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CheckoutItemDto {
    private UUID productId;
    private int amount;
}
