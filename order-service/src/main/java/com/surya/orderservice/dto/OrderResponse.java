package com.surya.orderservice.dto;

import java.math.BigDecimal;

public record OrderResponse(String orderNumber, String productId, BigDecimal price, Long quantity) {
}
