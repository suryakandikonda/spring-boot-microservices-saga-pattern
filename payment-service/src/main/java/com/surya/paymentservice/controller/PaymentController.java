package com.surya.paymentservice.controller;

import com.surya.microservices.dto.Payment.PaymentRequest;
import com.surya.microservices.dto.Payment.PaymentResponse;
import com.surya.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping
    public PaymentResponse getByOrderNumber(@RequestParam String orderNumber) {
        return paymentService.getByOrderNumber(orderNumber);
    }

}
