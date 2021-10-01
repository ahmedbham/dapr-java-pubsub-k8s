package com.example.pubsubdapr.producer.controller;

import com.example.pubsubdapr.producer.service.PubsubHttpService;
import com.example.pubsubdapr.producer.service.PubsubService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubsubController {

    @Autowired
    private PubsubService pubsubService;

    @Autowired
    private PubsubHttpService pubsubHttpService;

    @GetMapping("/generate")
    public String callProduce(@RequestParam String message){
        pubsubService.produce(message);
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
