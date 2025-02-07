package com.surya.orderservice.controller;

import com.surya.orderservice.dto.OrderRequest;
import com.surya.orderservice.dto.OrderResponse;
import com.surya.orderservice.model.Order;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/getOrderByOrderNumber")
    public Order getOrderByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getOrderByOrderNumber(orderNumber);
    }


}
