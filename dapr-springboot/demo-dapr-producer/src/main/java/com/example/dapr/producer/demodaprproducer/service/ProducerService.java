package com.example.dapr.producer.demodaprproducer.service;

import org.springframework.stereotype.Service;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
// import io.dapr.client.DaprHttp;

@Service
public class ProducerService {
    static final String PUBSUB_NAME = "messagebus";

    static final String TOPIC_NAME = "sample123";

    public void produce(String message){
        System.out.println("producing message: " + message);

        DaprClient client = (new DaprClientBuilder()).build();
            client.publishEvent(PUBSUB_NAME, TOPIC_NAME, message).block();
        
    }
}

