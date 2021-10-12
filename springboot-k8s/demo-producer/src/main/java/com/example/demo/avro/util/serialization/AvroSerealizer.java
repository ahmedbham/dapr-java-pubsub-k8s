package com.example.demo.avro.util.serialization;


// import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import example.avro.User;

public class AvroSerealizer implements org.apache.kafka.common.serialization.Serializer<User>{
    private static final Logger logger = LoggerFactory.getLogger(AvroSerealizer.class);
    // private final ObjectMapper objectMapper = new ObjectMapper();

    

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, User request) {
        try {
            if (request == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
            byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get()
                .jsonEncoder(User.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            logger.error("Serialization error " + e.getMessage());
        }
        return data;
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }

    @Override
    public void close() {
    }

    public byte[] serealizeUserJSON(User request) {
        DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get()
                .jsonEncoder(User.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            logger.error("Serialization error " + e.getMessage());
        }
        return data;
    }

    public byte[] serealizeUserBinary(User request) {
        DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = EncoderFactory.get()
            .binaryEncoder(stream, null);
        try {
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            logger.error("Serialization error " + e.getMessage());
        }

        return data;
    }
}
