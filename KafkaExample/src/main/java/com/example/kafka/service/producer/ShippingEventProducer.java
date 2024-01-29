package com.example.kafka.service.producer;

import com.example.kafka.modal.events.ShippingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingEventProducer {
    private static final String TOPIC = "shipping_events";

    private final KafkaTemplate<String, ShippingEvent> kafkaTemplate;

    public void sendShippingEvent(ShippingEvent shippingEvent) {
        kafkaTemplate.send(TOPIC, shippingEvent);
    }
}
