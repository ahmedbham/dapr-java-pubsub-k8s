package com.example.pubsubdapr.producer.controller;

import com.example.pubsubdapr.producer.service.PubsubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubsubController {

    @Autowired
    private PubsubService pubsubService;

    @GetMapping("/generate")
    public String callProduce(@RequestParam String message){
        pubsubService.produce(message);
        return "OK";
    }
    
}
