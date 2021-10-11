package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

// import com.example.demo.avro.model.AvroHttpRequest;

@Service
public class ConsumerService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @KafkaListener(topics = "messages", groupId = "message_group_id")
    public void consume(String avroHttpRequest){
    // public void consume(AvroHttpRequest avroHttpRequest){
        try{
            // System.out.println("consuming the message " + OBJECT_MAPPER.writeValueAsString(avroHttpRequest));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
