package com.example.kafka.modal.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Long quantity = 1L;
    private String status = "ORDER_CREATED";
    // Other fields, constructors, getters, setters
}