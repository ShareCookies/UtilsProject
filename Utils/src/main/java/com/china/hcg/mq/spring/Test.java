package com.china.hcg.mq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/testmq")
    public void testmq(String message) {
        kafkaProducerService.send(message);
    }
}