package com.example.kafka.controller;

import com.example.kafka.modal.OrderRequest;
import com.example.kafka.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) throws InterruptedException {
        // Process the received order event and send it to Kafka
        String orderMsg = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(orderMsg);
    }
}