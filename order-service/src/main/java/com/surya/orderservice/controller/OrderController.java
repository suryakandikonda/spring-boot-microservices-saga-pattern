package com.surya.orderservice.controller;

import com.surya.microservices.dto.Order.OrderRequest;
import com.surya.microservices.dto.Order.OrderResponse;
import com.surya.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/getOrderByOrderNumber")
    public OrderResponse getOrderByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }


}
