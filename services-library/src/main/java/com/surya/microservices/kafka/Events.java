package com.surya.microservices.kafka;

public enum Events {

    // ORDER Events
    ORDER_CREATED,
    COMPLETE_ORDER,
    ORDER_FAILED,

    // INVENTORY Events
    ROLLBACK_INVENTORY,

    // PAYMENT Events
    PROCESS_PAYMENT,
    PAYMENT_PROCESSED,
    PAYMENT_FAILED

    // Order Created -> Process Payment -> Payment Processed -> Complete Order

    // Order Created -> Process Payment -> Payment Failed -> Rollback Inventory -> Order Failed

}
