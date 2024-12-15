package com.wizh.SalesApp.dto;

import lombok.Builder;

@Builder
public record CheckoutResponseDto(String status, String message) {
}
