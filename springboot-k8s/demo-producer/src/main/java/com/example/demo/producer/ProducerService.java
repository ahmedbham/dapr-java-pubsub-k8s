package com.example.demo.producer;

import java.util.Map;
import java.util.HashMap;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;


@Service
public class ProducerService {

    // @Bean
    // public ProducerFactory<String, String> producerFactory() {
    //     return new DefaultKafkaProducerFactory<>(producerConfigs());
    // }

    // @Bean
    // public Map<String, Object> producerConfigs() {
    //     Map<String, Object> props = new HashMap<String, Object>();
    //     props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    //     props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     // See https://kafka.apache.org/documentation/#producerconfigs for more properties
    //     return props;
    // }

    // @Bean
    // public KafkaTemplate<String, String> kafkaTemplate() {
    //     return new KafkaTemplate<String, String>(producerFactory());
    // }           


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String message){
        System.out.println("producing message: " + message);
        kafkaTemplate.send("messages", message);
    }
}
