package com.surya.paymentservice.controller;

import com.surya.paymentservice.model.Payment;
import com.surya.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment.getOrderNumber(), payment.getPrice());
    }

    @GetMapping
    public Payment getByOrderNumber(@RequestParam String orderNumber) {
        return paymentService.findByOrderNumber(orderNumber);
    }

}
