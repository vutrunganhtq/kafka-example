package com.example.kafka.controller;

import com.example.kafka.modal.events.PaymentEvent;
import com.example.kafka.service.producer.PaymentEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentEventProducer paymentEventProducer;

    @PostMapping("/payments")
    public ResponseEntity<String> processPayment(@RequestBody PaymentEvent paymentEvent) {
        // Process the received payment event and send it to Kafka
        paymentEventProducer.sendPaymentEvent(paymentEvent);
        return ResponseEntity.ok("Payment processed successfully");
    }
}