package com.example.kafka.service.producer;

import com.example.kafka.modal.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private static final String TOPIC_PAYMENT_EVENT = "payment_events";
    private static final String TOPIC_PAYMENT_MSG_EVENT = "payment_msg_events";

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void sendPaymentEvent(PaymentEvent paymentEvent) {
        kafkaTemplate.send(TOPIC_PAYMENT_EVENT, paymentEvent);
    }

    public void sendPaymentMsgEvent(PaymentEvent paymentEvent) {
        kafkaTemplate.send(TOPIC_PAYMENT_MSG_EVENT, paymentEvent);
    }
}