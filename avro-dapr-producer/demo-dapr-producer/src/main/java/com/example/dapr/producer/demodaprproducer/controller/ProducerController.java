package com.example.dapr.producer.demodaprproducer.controller;

import com.example.dapr.producer.demodaprproducer.service.ProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/generate")
    public String generate(@RequestParam String message){
        producerService.produce(message);
        return "OK";
    }
}
