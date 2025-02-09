package com.surya.paymentservice.kafka;

import com.surya.microservices.dto.Order.OrderEvent;
import com.surya.microservices.dto.Payment.PaymentRequest;
import com.surya.microservices.dto.Payment.PaymentResponse;
import com.surya.microservices.kafka.Events;
import com.surya.microservices.kafka.KafkaTopic;
import com.surya.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentKafkaListener {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "payment-topic", groupId = "order-group")
    public void handlePaymentTopics(OrderEvent orderEvent) throws InterruptedException {
        if(orderEvent.event().equals(Events.PROCESS_PAYMENT)) {
            PaymentResponse paymentResponse = paymentService.createPayment(new PaymentRequest(orderEvent.orderRequest().orderNumber(), orderEvent.orderRequest().price()));
            if(paymentResponse != null) {
                Thread.sleep(5000);
                /*paymentService.makePaymentSuccess(orderEvent.orderRequest().orderNumber());
                kafkaTemplate.send(KafkaTopic.SAGA_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.PAYMENT_PROCESSED));
                log.info("Payment for Order: {} is Processed", orderEvent.orderRequest().orderNumber());*/

                paymentService.makePaymentFailed(orderEvent.orderRequest().orderNumber());
                kafkaTemplate.send(KafkaTopic.SAGA_TOPIC.getTopicName(), new OrderEvent(orderEvent.orderRequest(), Events.PAYMENT_FAILED));
                log.info("Payment for Order: {} is Processed", orderEvent.orderRequest().orderNumber());
            }
        }
    }

}
