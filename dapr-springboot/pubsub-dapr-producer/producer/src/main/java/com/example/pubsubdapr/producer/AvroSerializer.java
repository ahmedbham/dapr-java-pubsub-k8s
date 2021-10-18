package com.example.pubsubdapr.producer;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class AvroSerializer {
    public static byte[] serialize(User request) {
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
            System.out.println("Serialization error " + e.getMessage());
        }
        return data;
        } catch (Exception e) {
            throw new RuntimeException("Error when serializing MessageDto to byte[]");
        }
    }
    
}
