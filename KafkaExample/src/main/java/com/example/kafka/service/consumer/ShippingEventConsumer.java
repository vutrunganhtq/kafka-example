package com.example.kafka.service.consumer;

import com.example.kafka.modal.events.ShippingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingEventConsumer {

    @KafkaListener(topics = "shipping_events", groupId = "shipping_group")
    public void handleOrderEvent(ShippingEvent shippingEvent) {
        // Process the shipping event, e.g., store it in the database
        log.info("Received Shipping Event: " + shippingEvent);
        // Implement the logic for shipping processing and updating inventory
    }
}
