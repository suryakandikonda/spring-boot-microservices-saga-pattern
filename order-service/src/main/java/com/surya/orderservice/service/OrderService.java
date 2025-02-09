package com.surya.orderservice.service;

import com.surya.microservices.dto.Inventory.InventoryRequest;
import com.surya.microservices.dto.Order.OrderResponse;
import com.surya.orderservice.OrderStatus;
import com.surya.orderservice.client.InventoryClient;
import com.surya.microservices.dto.Order.OrderRequest;
import com.surya.microservices.model.Order;
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

    private Order save(Order order) {
        log.trace("save()");
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            log.error("save() Exception:{}", e.getMessage());
        }
        return order;
    }

    private Order findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        log.trace("getOrderByOrderNumber()");
        try {
            Order order = findByOrderNumber(orderNumber);
            return new OrderResponse(order.getOrderNumber(), order.getProductId(), order.getPrice(), order.getQuantity(), order.getOrderStatus());
        } catch (Exception e) {
            log.error("getOrderByOrderNumber() Exception: {}", e.getMessage());
        }
        return null;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) throws Exception {
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
            InventoryRequest inventoryRequest = new InventoryRequest(order.getProductId(), "", order.getQuantity());
            inventoryClient.decreaseProductStock(inventoryRequest);
            return new OrderResponse(order.getOrderNumber(), order.getProductId(), order.getPrice(), order.getQuantity(), order.getOrderStatus());
        } else {
            stringBuilder = new StringBuilder("Product is not is Stock. Product ID: ").append(orderRequest.productId());
            throw new Exception("Product Not is Stock");
        }
    }

    public OrderResponse markPaymentAsCompleted(String orderNumber) {
        log.trace("markPaymentAsCompleted()");
        try {
            Order order = findByOrderNumber(orderNumber);
            order.setOrderStatus(OrderStatus.PAYMENT_COMPLETED.name());
            order = save(order);
            return new OrderResponse(order.getOrderNumber(), order.getProductId(), order.getPrice(), order.getQuantity(), order.getOrderStatus());
        } catch (Exception e) {
            log.error("markPaymentAsCompleted() Exception: {}", e.getMessage());
        }
        return null;
    }

}
