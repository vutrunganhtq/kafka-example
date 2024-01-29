package com.example.kafka.service.consumer;

import com.example.kafka.constant.StatusEnum;
import com.example.kafka.modal.events.PaymentEvent;
import com.example.kafka.modal.events.ShippingEvent;
import com.example.kafka.service.producer.PaymentEventProducer;
import com.example.kafka.service.producer.ShippingEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final ShippingEventProducer shippingEventProducer;
    private final PaymentEventProducer paymentMsgEventProducer;

    @KafkaListener(topics = "payment_events", groupId = "payment_group")
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
       log.info("Received Payment Event: {}", paymentEvent);

        paymentEvent.setStatus(StatusEnum.PAYMENT_FAIL.getName());
        if (isPaymentSuccess(paymentEvent.getOrderId())) {
            paymentEvent.setStatus(StatusEnum.PAYMENT_SUCCESS.getName());
        }

        log.info("Sending Payment Msg Event Output: {}", paymentEvent);
        paymentMsgEventProducer.sendPaymentMsgEvent(paymentEvent);

        if(StatusEnum.PAYMENT_FAIL.getName().equals(paymentEvent.getStatus())){
            return;
        }

        // Process the payment event, e.g., update payment status
        // Implement the logic for payment processing and order status update

        // payment done - start shipping event
        shippingEventProducer.sendShippingEvent(new ShippingEvent(1L, "Hanoi", StatusEnum.DELIVERING.getName()));
    }

    private boolean isPaymentSuccess(Long id) {
        Random random = new Random();
        return random.nextBoolean();
    }


}