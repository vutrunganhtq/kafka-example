package com.example.kafka.service;

import com.example.kafka.modal.OrderRequest;

public interface OrderService {
    String createOrder(OrderRequest orderRequest) throws InterruptedException;
}
