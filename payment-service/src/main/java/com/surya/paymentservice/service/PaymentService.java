package com.surya.paymentservice.service;

import com.surya.microservices.dto.Payment.PaymentRequest;
import com.surya.microservices.dto.Payment.PaymentResponse;
import com.surya.paymentservice.enums.PaymentStatus;
import com.surya.microservices.model.Payment;
import com.surya.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment save(Payment payment) {
        log.trace("save()");
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            log.error("save() Exception: {}", e.getMessage());
        }
        return payment;
    }

    private Payment findByOrderNumber(String orderNumber) {
        log.trace("findByOrderNumber()");
        try {
            return paymentRepository.findByOrderNumber(orderNumber);
        } catch (Exception e) {
            log.error("findByOrderNumber() Exception: {}", e.getMessage());
        }
        return null;
    }

    public PaymentResponse getByOrderNumber(String orderNumber) {
        log.trace("getByOrderNummber()");
        try {
            Payment payment = paymentRepository.findByOrderNumber(orderNumber);
            return new PaymentResponse(payment.getOrderNumber(), payment.getPrice(), payment.getPaymentStatus());
        } catch (Exception e) {
            log.error("getByOrderNummber() Exception: {}", e.getMessage());
        }
        return null;
    }


    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        log.trace("createPayment()");
        try {
            Payment existPayment = findByOrderNumber(paymentRequest.orderNumber());
            if(existPayment != null) {
                return new PaymentResponse(existPayment.getOrderNumber(), existPayment.getPrice(), existPayment.getPaymentStatus());
            }
            else {
                existPayment = Payment.builder()
                        .orderNumber(paymentRequest.orderNumber())
                        .price(paymentRequest.price())
                        .paymentStatus(PaymentStatus.PROCESSING.name())
                        .build();
                existPayment = save(existPayment);
                return new PaymentResponse(existPayment.getOrderNumber(), existPayment.getPrice(), existPayment.getPaymentStatus());
            }
        } catch (Exception e) {
            log.error("createPayment() Exception: {}", e.getMessage());
        }
        return null;
    }

    public Payment makePaymentSuccess(String orderNumber) {
        log.trace("makePaymentSuccess()");
        try {
            Payment payment = findByOrderNumber(orderNumber);
            if(payment != null) {
                payment.setPaymentStatus(PaymentStatus.SUCCESS.name());
                return save(payment);
            }
        } catch (Exception e) {
            log.error("makePaymentSuccess() Exception: {}", e.getMessage());
        }
        return null;
    }

    public Payment makePaymentFailed(String orderNumber) {
        log.trace("makePaymentFailed()");
        try {
            Payment payment = findByOrderNumber(orderNumber);
            if(payment != null) {
                payment.setPaymentStatus(PaymentStatus.FAILED.name());
                return save(payment);
            }
        } catch (Exception e) {
            log.error("makePaymentFailed() Exception: {}", e.getMessage());
        }
        return null;
    }


}
