package com.surya.microservices.dto.Order;

import com.surya.microservices.kafka.Events;

public record OrderEvent(OrderRequest orderRequest, Events event) {
}
