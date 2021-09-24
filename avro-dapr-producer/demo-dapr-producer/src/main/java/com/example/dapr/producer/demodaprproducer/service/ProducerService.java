package com.example.dapr.producer.demodaprproducer.service;

import org.springframework.stereotype.Service;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
// import io.dapr.client.DaprHttp;

@Service
public class ProducerService {
    static final String BINDING_NAME = "sample123";

    static final String BINDING_OPERATION = "create";

    public void produce(String message){
        System.out.println("producing message: " + message);

        DaprClient client = (new DaprClientBuilder()).build();
            client.invokeBinding(BINDING_NAME, BINDING_OPERATION, message).block();
        
        
    }
}

