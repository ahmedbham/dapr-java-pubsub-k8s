package com.example.demo.producer;

// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.clients.producer.ProducerRecord;
// import org.apache.kafka.common.serialization.StringSerializer;
// import io.confluent.kafka.serializers.KafkaAvroSerializer;
// import java.util.Properties;
// import java.util.Map;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;

// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.stereotype.Service;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.apache.kafka.common.serialization.StringSerializer;
// import com.example.demo.avro.util.serialization.AvroSerealizer;
// import com.example.demo.avro.model.*;


// @Service
public class AvroProducerService {
    // @Bean
    // public ProducerFactory<String, Object> producerFactory() {
    //     return new DefaultKafkaProducerFactory<>(producerConfigs());
    // }

    // @Bean
    // public Map<String, Object> producerConfigs() {
    //     Map<String, Object> props = new HashMap<String, Object>();
    //     props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dapr-kafka.kafka:9092");
    //     props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    //     props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, com.example.demo.avro.util.serialization.AvroSerealizer.class);
    //     // See https://kafka.apache.org/documentation/#producerconfigs for more properties
    //     return props;
    // }

    // @Bean
    // public KafkaTemplate<String, Object> kafkaTemplate() {
    //     return new KafkaTemplate<String, Object>(producerFactory());
    // }           

   
    // private KafkaTemplate<String, Object> kafkaTemplate = kafkaTemplate();

    // public void produce(String message){
    //     ClientIdentifier clientIdentifier = new ClientIdentifier("host1", "10.0.0.1");
    //     List<java.lang.CharSequence> employeeNames = Arrays.asList("amp1", "emp2");
    //     // Active active = new Active();
    //     AvroHttpRequest request = new AvroHttpRequest(100L, clientIdentifier, employeeNames, com.example.demo.avro.model.Active.YES );
    //     // AvroSerealizer serealizer = new AvroSerealizer();
    //     // byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
        
    //     System.out.println("producing message: " + request.toString());
    //     kafkaTemplate.send("messages", request);
    // }
    
}
