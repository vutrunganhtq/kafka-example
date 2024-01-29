package com.example.kafka.modal;

import lombok.Data;

@Data
public class OrderRequest {
    private Long orderId;
    private Long quantity = 1L;
    private String status = "ORDER_CREATED";
}
