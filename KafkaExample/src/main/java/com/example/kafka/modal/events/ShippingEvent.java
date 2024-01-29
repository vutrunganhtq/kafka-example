package com.example.kafka.modal.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingEvent {
    private Long shippingId;
    private String location;
    private String status;
}
