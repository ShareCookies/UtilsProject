package com.china.hcg.mq.spring;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "my-topic", group = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}