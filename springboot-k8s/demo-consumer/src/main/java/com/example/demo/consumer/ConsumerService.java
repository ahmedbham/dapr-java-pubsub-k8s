package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.avro.User;
@Service
public class ConsumerService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @KafkaListener(topics = "messages", groupId = "message_group_id")
    public void consume(User user){
        try{
            // System.out.println("consuming the message " + OBJECT_MAPPER.writeValueAsString(user));
            System.out.println("consuming the message ");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
