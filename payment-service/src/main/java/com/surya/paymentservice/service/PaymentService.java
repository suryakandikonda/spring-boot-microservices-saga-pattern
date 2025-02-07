package com.surya.paymentservice.service;

import com.surya.paymentservice.enums.PaymentStatus;
import com.surya.paymentservice.model.Payment;
import com.surya.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        log.trace("save()");
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            log.error("save() Exception: {}", e.getMessage());
        }
        return payment;
    }

    public Payment findByOrderNumber(String orderNumber) {
        log.trace("findByOrderNumber()");
        try {
            return paymentRepository.findByOrderNumber(orderNumber);
        } catch (Exception e) {
            log.error("findByOrderNumber() Exception: {}", e.getMessage());
        }
        return null;
    }


    public Payment createPayment(String orderNumber, BigDecimal price) {
        log.trace("createPayment()");
        try {
            Payment existPayment = findByOrderNumber(orderNumber);
            if(existPayment != null) return existPayment;
            else {
                Payment payment = Payment.builder()
                        .orderNumber(orderNumber)
                        .price(price)
                        .paymentStatus(PaymentStatus.PROCESSING.name())
                        .build();
                return save(payment);
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
