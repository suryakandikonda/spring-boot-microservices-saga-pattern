package com.surya.orderservice.dto;

import java.math.BigDecimal;

public record OrderRequest(String id, String orderNumber, String productId, BigDecimal price, Long quantity) {
}
