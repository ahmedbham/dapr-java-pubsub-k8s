package com.example.demo.producer;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.apache.kafka.common.serialization.StringSerializer;
import com.example.demo.avro.util.serialization.AvroSerealizer;
import com.example.demo.avro.model.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

@Service
public class PubsubHttpService {
    
    public void httpPostRequest(String message) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

            ClientIdentifier clientIdentifier = new ClientIdentifier("host1", "10.0.0.1");
            List<java.lang.CharSequence> employeeNames = Arrays.asList("amp1", "emp2");
            // Active active = new Active();
            AvroHttpRequest request = new AvroHttpRequest(100L, clientIdentifier, employeeNames, com.example.demo.avro.model.Active.YES );
            AvroSerealizer avroSerealizer = new AvroSerealizer();
            byte[] data = avroSerealizer.serealizeAvroHttpRequestJSON(request);
            avroSerealizer.close();

        HttpRequest httprequest = HttpRequest.newBuilder(new URI("http://localhost:3500/v1.0/publish/messagebus/avrotopic"))
            .version(HttpClient.Version.HTTP_1_1)
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofByteArray(data))
            // .headers("dapr-app-id", "messagebus")
            .build();
        HttpResponse<String> response = client.send(httprequest, BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    } 
    
}
