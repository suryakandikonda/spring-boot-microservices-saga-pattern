package com.surya.orderservice.kafka;

import com.surya.microservices.dto.Order.OrderEvent;
import com.surya.microservices.kafka.Events;
import com.surya.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderKafkaListener {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void handleOrderTopics(OrderEvent orderEvent) {
        if(orderEvent.event().equals(Events.COMPLETE_ORDER)) {
            orderService.markPaymentAsCompleted(orderEvent.orderRequest().orderNumber());
            log.info("Order with Order Number: {} is Completed", orderEvent.orderRequest().orderNumber());
        } else if(orderEvent.event().equals(Events.ORDER_FAILED)) {
            orderService.markPaymentAsFailed(orderEvent.orderRequest().orderNumber());
            log.info("Order with Order Number: {} is Payment Failed", orderEvent.orderRequest().orderNumber());
        }
    }

}
