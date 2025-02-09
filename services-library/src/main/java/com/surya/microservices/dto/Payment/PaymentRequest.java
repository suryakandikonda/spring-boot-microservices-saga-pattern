package com.surya.microservices.dto.Payment;

import java.math.BigDecimal;

public record PaymentRequest(String orderNumber, BigDecimal price) {
}
