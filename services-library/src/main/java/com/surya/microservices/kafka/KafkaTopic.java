package com.surya.microservices.kafka;

import lombok.Getter;

public enum KafkaTopic {
    SAGA_TOPIC("saga-topic"),
    ORDER_TOPIC("order-topic"),
    INVENTORY_TOPIC("inventory-topic"),
    PAYMENT_TOPIC("payment-topic");

    @Getter
    private String topicName;

    KafkaTopic(String s) {
        this.topicName = s;
    }
}
