package com.example.demo.controller;

import com.example.demo.producer.ProducerService;
import com.example.demo.producer.PubsubHttpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private PubsubHttpService pubsubHttpService;

    @GetMapping("/generate")
    public String generate(@RequestParam String message){
        producerService.produce(message);
        return "OK";
    }

    @GetMapping("/httppost")
    public String makeHttpPostRequest(@RequestParam String message){
        try{
            System.out.println("call pubsubhttpservice");
            pubsubHttpService.httpPostRequest(message);
        }
        catch(Exception e){System.out.println("there was an exception");}
        return "OK";
    }
    
}
