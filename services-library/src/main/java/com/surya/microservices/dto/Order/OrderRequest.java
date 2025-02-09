package com.surya.microservices.dto.Order;

import java.math.BigDecimal;

public record OrderRequest(String orderNumber, String productId, BigDecimal price, Long quantity) {
}
