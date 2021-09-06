package com.example.dapr.consumer.demodaprconsumer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class InputBindingController {
    @PostMapping(path = "/sample123")
    public Mono<String> handleInputBinding(@RequestBody(required = false) byte[] body) {
        return Mono.fromRunnable(() ->
        System.out.println("Received message through binding: " + (body == null ? "" : new String(body))));
    }
}
