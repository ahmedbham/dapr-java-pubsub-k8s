package com.example.pubsubdapr.producer.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.pubsubdapr.producer.Item;

@Service
public class PublishJsonNoCE {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void httpNoCeRequest(String message) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        Item item = new Item("bob", "bar");

        String json_msg = OBJECT_MAPPER.writeValueAsString(item);

        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:3500/v1.0/publish/messagebus/testingtopic?metadata.rawPayload=true"))
            .version(HttpClient.Version.HTTP_1_1)
            .POST(BodyPublishers.ofString(json_msg) )
            .headers("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    } 
    
}
