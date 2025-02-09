package com.surya.orchestrator.service;

import com.surya.microservices.dto.Order.OrderEvent;
import com.surya.microservices.kafka.Events;
import com.surya.microservices.kafka.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SagaOrchestrator {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @KafkaListener(topics = "saga-topic", groupId = "order-group")
    public void handleSaga(OrderEvent orderEvent) {
        log.info("handleSaga() Event Listened:{}", orderEvent.event().name());
        switch (orderEvent.event()) {
            case ORDER_CREATED:
                kafkaTemplate.send(KafkaTopic.PAYMENT_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.PROCESS_PAYMENT));
                break;

            case PAYMENT_PROCESSED:
                kafkaTemplate.send(KafkaTopic.ORDER_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.COMPLETE_ORDER));
                break;

            case PAYMENT_FAILED:
                kafkaTemplate.send(KafkaTopic.INVENTORY_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.ROLLBACK_INVENTORY));
                kafkaTemplate.send(KafkaTopic.ORDER_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.ORDER_FAILED));
                break;
        }
    }

}
