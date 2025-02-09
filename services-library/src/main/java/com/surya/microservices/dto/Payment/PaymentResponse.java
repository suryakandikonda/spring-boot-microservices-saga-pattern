package com.surya.microservices.dto.Payment;

import java.math.BigDecimal;

public record PaymentResponse(String orderNumber, BigDecimal price, String paymentStatus) {
}
