package com.example.democonsumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "messages", groupId = "message_group_id")
    public void consume(String message){
        System.out.println("consuming the message " + message);
    }
    
}
