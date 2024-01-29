package com.example.kafka.service.consumer;

import com.example.kafka.constant.StatusEnum;
import com.example.kafka.modal.events.OrderEvent;
import com.example.kafka.modal.events.PaymentEvent;
import com.example.kafka.service.producer.PaymentEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final PaymentEventProducer paymentEventProducer;

    @KafkaListener(topics = "order_events", groupId = "order_group")
    public void handleOrderEvent(OrderEvent orderEvent) {

        // Process the order event, e.g., store it in the database
        orderEvent.setStatus(StatusEnum.ORDER_PROCESSING.getName());
        log.info("Received Order Event: " + orderEvent);
        // Implement the logic for order processing and updating inventory

        // Done order send request payment
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(orderEvent.getOrderId());
        paymentEvent.setAmount(BigDecimal.TEN);
        paymentEvent.setStatus(StatusEnum.PAYMENT_PROCESSING.getName());
        paymentEventProducer.sendPaymentEvent(paymentEvent);
    }
}