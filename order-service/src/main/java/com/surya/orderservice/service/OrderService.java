package com.surya.orderservice.service;

import com.surya.orderservice.OrderStatus;
import com.surya.orderservice.client.InventoryClient;
import com.surya.orderservice.dto.Inventory;
import com.surya.orderservice.dto.OrderRequest;
import com.surya.orderservice.model.Order;
import com.surya.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    public Order save(Order order) {
        log.trace("save()");
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            log.error("save() Exception:{}", e.getMessage());
        }
        return order;
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        log.trace("getOrderByOrderNumber()");
        try {
            return orderRepository.findByOrderNumber(orderNumber);
        } catch (Exception e) {
            log.error("getOrderByOrderNumber() Exception: {}", e.getMessage());
        }
        return null;
    }

    public String placeOrder(OrderRequest orderRequest) {
        StringBuilder stringBuilder;
        boolean isInStock = inventoryClient.isInStock(orderRequest.productId(), orderRequest.quantity());
        if(isInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .productId(orderRequest.productId())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .orderStatus(OrderStatus.PAYMENT_PENDING.name())
                    .build();

            orderRepository.save(order);
            Inventory inventory = Inventory.builder().productId(order.getProductId()).qty(order.getQuantity()).build();
            inventoryClient.decreaseProductStock(inventory);
            stringBuilder = new StringBuilder("Order placed successfully with Order Number:").append(order.getOrderNumber());
        } else {
            stringBuilder = new StringBuilder("Product is not is Stock. Product ID: ").append(orderRequest.productId());
        }
        log.info(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void markPaymentAsCompleted(String orderNumber) {
        log.trace("markPaymentAsCompleted()");
        try {
            Order order = getOrderByOrderNumber(orderNumber);
            order.setOrderStatus(OrderStatus.PAYMENT_COMPLETED.name());
            save(order);
        } catch (Exception e) {
            log.error("markPaymentAsCompleted() Exception: {}", e.getMessage());
        }
    }

}
