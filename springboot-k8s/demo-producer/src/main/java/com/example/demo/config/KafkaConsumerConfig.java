package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.avro.model.AvroHttpRequest;
import com.example.demo.avro.util.serialization.AvroDeSerealizer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, AvroHttpRequest> consumerFactory() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "dapr-kafka.kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "shine-local-avro");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeSerealizer.class);
               // See https://kafka.apache.org/documentation/#producerconfigs for more properties
               return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new AvroDeSerealizer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AvroHttpRequest> kafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, AvroHttpRequest> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
