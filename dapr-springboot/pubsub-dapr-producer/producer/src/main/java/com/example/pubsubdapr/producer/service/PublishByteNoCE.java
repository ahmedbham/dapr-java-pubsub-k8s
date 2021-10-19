package com.example.pubsubdapr.producer.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
// import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.pubsubdapr.producer.AvroSerializer;
import com.example.pubsubdapr.producer.Item;
import com.example.pubsubdapr.producer.User;

@Service
public class PublishByteNoCE {

    
    public void httpByteNoCeRequest(String message) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

            User user = User.newBuilder()
            .setName("Charlie")
            .setFavoriteColor("blue")
            .setFavoriteNumber(null)
            .build();

            byte[] byte_msg = AvroSerializer.serialize(user);


        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:3500/v1.0/publish/messagebus/bytetopic?metadata.rawPayload=true"))
            .version(HttpClient.Version.HTTP_1_1)
            .POST(BodyPublishers.ofByteArray(byte_msg) )
            .headers("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    } 
    
}
