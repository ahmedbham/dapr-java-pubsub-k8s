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

import example.avro.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.apache.kafka.common.serialization.StringSerializer;
import com.example.demo.avro.util.serialization.AvroSerealizer;

@Slf4j
@Service
public class ProducerService {

    @Bean
    public ProducerFactory<String, User> producerFactory() {
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
    public KafkaTemplate<String, User> kafkaTemplate() {
        return new KafkaTemplate<String, User>(producerFactory());
    }           

   @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void produce(String message){

      User user = User.newBuilder()
             .setName("Charlie")
             .setFavoriteColor("blue")
             .setFavoriteNumber(null)
             .build();
        
        // AvroSerealizer serealizer = new AvroSerealizer();
        // byte[] data = serealizer.serealizeUserJSON(request);
        
        System.out.println("producing message: " + user.toString());
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send("messages", user);
        future.addCallback(
        new ListenableFutureCallback<SendResult<String, User>>() {
          
          @Override
          public void onSuccess(SendResult<String, User> result) {
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
