package com.example.dapr.consumer.demodaprconsumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.example.pubsubdapr.producer.User;
import com.example.pubsubdapr.producer.AvroDeserializer;

@RestController
public class WithoutCloudEventJsonController {
    
    @Topic(name = "bytetopic", pubsubName = "messagebus")
    @PostMapping(path = "/bytetopic")
    public Mono<Void> handleInputBinding(@RequestBody (required = false) byte[] data) {
      
        return Mono.fromRunnable(() -> {
            try {
              
                User user = AvroDeserializer.deserialize(data);
                System.out.println("Subscriber got: " + user.toString());
              
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });
        // return Mono.fromRunnable(() ->
        // System.out.println("Received message through binding: " + (body == null ? "" : new String(body))));
    }
}