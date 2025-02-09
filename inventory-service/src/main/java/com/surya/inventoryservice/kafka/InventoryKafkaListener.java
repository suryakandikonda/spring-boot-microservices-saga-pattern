package com.surya.inventoryservice.kafka;

import com.surya.inventoryservice.service.InventoryService;
import com.surya.microservices.dto.Inventory.InventoryRequest;
import com.surya.microservices.dto.Order.OrderEvent;
import com.surya.microservices.kafka.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryKafkaListener {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "inventory-topic", groupId = "order-group")
    public void handleInventoryTopic(OrderEvent orderEvent) {
        if(orderEvent.event().equals(Events.ROLLBACK_INVENTORY)) {
            inventoryService.increaseProductStock(new InventoryRequest(orderEvent.orderRequest().productId(), "", orderEvent.orderRequest().quantity()));
            log.info("Inventory is Rolled back for Product: {}", orderEvent.orderRequest().productId());
        }
    }

}
