package com.surya.microservices.dto.Order;

import java.math.BigDecimal;

public record OrderResponse(String orderNumber, String productId, BigDecimal price, Long quantity, String orderStatus) {
}
