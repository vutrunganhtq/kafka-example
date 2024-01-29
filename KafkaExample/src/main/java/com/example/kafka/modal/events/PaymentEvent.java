package com.example.kafka.modal.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentEvent {
    private Long orderId;
    private BigDecimal amount;
    private String status;
    // Other fields, constructors, getters, setters
}