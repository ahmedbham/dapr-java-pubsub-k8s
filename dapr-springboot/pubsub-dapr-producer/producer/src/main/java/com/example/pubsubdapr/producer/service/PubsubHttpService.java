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

@Service
public class PubsubHttpService {
    // public static void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
    //     HttpClient client = HttpClient.newHttpClient();
    //     HttpRequest request = HttpRequest.newBuilder()
    //         .version(HttpClient.Version.HTTP_2)
    //         .uri(URI.create("http://jsonplaceholder.typicode.com/posts/1"))
    //         .headers("Accept-Enconding", "gzip, deflate")
    //         .build();
    //     HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

    //     String responseBody = response.body();
    //     int responseStatusCode = response.statusCode();

    //     System.out.println("httpGetRequest: " + responseBody);
    //     System.out.println("httpGetRequest status code: " + responseStatusCode);
    // }

    public void httpPostRequest(String message) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:3500/v1.0/publish/messagebus/testingtopic"))
            .version(HttpClient.Version.HTTP_1_1)
            .POST(BodyPublishers.ofString(message))
            // .headers("dapr-app-id", "messagebus")
            .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    } 
    
}
