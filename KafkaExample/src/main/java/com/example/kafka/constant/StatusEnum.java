package com.example.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    ORDER_PLACED("1", "ORDER_PLACED"),
    ORDER_PROCESSING("2", "ORDER_PROCESSING"),
    PAYMENT_PROCESSING("3", "PAYMENT_PROCESSING"),
    PAYMENT_SUCCESS("4", "PAYMENT_SUCCESS"),
    PAYMENT_FAIL("5", "PAYMENT_FAIL"),
    DELIVERING("6", "DELIVERING"),
    ;
    private String value;
    private String name;
}
