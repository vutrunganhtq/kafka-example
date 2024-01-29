package com.example.kafka.service.impl;

import com.example.kafka.constant.StatusEnum;
import com.example.kafka.modal.OrderRequest;
import com.example.kafka.modal.events.OrderEvent;
import com.example.kafka.modal.events.PaymentEvent;
import com.example.kafka.service.OrderService;
import com.example.kafka.service.producer.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderEventProducer orderEventProducer;

    private final Map<Long, Object> lockMap = new HashMap<>();
    private PaymentEvent paymentMsgEventObject;

    @Override
    public String createOrder(OrderRequest orderRequest) throws InterruptedException {
        if (!isInStock(orderRequest.getOrderId(), orderRequest.getQuantity())) { // simulate cases by random value
            return "Out of stock";
        }

        orderRequest.setStatus(StatusEnum.ORDER_PLACED.getName());
        log.info("Received Order Event: " + orderRequest);

        orderEventProducer.sendOrderEvent(initEventObj(orderRequest));

        PaymentEvent paymentEvent = waitForPaymentMsgEvents(orderRequest.getOrderId());
        return StatusEnum.PAYMENT_SUCCESS.getName().equals(paymentEvent.getStatus()) ? "Order created successfully" : "Order payment fail";
    }

    private boolean isInStock(Long id, Long quantity) {
        Random random = new Random();
        return random.nextBoolean();
    }

    private OrderEvent initEventObj(OrderRequest orderRequest) {
        OrderEvent orderEvent = new OrderEvent();
        BeanUtils.copyProperties(orderRequest, orderEvent);
        return orderEvent;
    }

    @KafkaListener(topics = "payment_msg_events", groupId = "payment_msg_group")
    public void handlePaymentMsgEvent(PaymentEvent paymentEvent) {
        synchronized (getLock(paymentEvent.getOrderId())) {
            log.info("Received Payment Msg Event Output: {}", paymentEvent);
            paymentMsgEventObject = paymentEvent;
            getLock(paymentEvent.getOrderId()).notify();
        }
    }

    private PaymentEvent waitForPaymentMsgEvents(Long id) throws InterruptedException {
        synchronized (getLock(id)) {
            log.info(String.format("orderId: %s", id));
            if (paymentMsgEventObject == null || !Objects.equals(paymentMsgEventObject.getOrderId(), id)) {
                getLock(id).wait();
            }
            this.removeLock(id);
            return paymentMsgEventObject;
        }
    }

    private Object getLock(Long orderId) {
        synchronized (lockMap) {
            if (!lockMap.containsKey(orderId)) {
                lockMap.put(orderId, new Object());
            }
            return lockMap.get(orderId);
        }
    }

    private void removeLock(Long orderId) {
        synchronized (lockMap) {
            lockMap.remove(orderId);
        }
    }
}
