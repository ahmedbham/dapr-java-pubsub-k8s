package com.example.demo.producer;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Service
public class ProducerService {

    @Bean
    public ProducerFactory<String, AvroHttpRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dapr-kafka.kafka:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerealizer.class);
               // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return props;
    }

    @Bean
    public KafkaTemplate<String, AvroHttpRequest> kafkaTemplate() {
        return new KafkaTemplate<String, AvroHttpRequest>(producerFactory());
    }           

   @Autowired
    private KafkaTemplate<String, AvroHttpRequest> kafkaTemplate;

    public void produce(String message){
        ClientIdentifier clientIdentifier = new ClientIdentifier("host1", "10.0.0.1");
        List<java.lang.CharSequence> employeeNames = Arrays.asList("amp1", "emp2");
        // Active active = new Active();
        AvroHttpRequest request = new AvroHttpRequest(100L, clientIdentifier, employeeNames, com.example.demo.avro.model.Active.YES );
        // AvroSerealizer serealizer = new AvroSerealizer();
        // byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
        
        System.out.println("producing message: " + request.toString());
        ListenableFuture<SendResult<String, AvroHttpRequest>> future = kafkaTemplate.send("messages", request);
        future.addCallback(
        new ListenableFutureCallback<SendResult<String, AvroHttpRequest>>() {
          
          @Override
          public void onSuccess(SendResult<String, AvroHttpRequest> result) {
            System.out.println("offset: " + result.getRecordMetadata().offset());
            // log.info(
            //     "Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
          }

          @Override
          public void onFailure(Throwable ex) {
            System.out.println("Unable to send message: " + ex.getMessage());
            // log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
          }
        });

    }
}
