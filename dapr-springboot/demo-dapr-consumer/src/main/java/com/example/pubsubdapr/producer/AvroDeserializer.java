package com.example.pubsubdapr.producer;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class AvroDeserializer {
    public static User deserialize(byte[] data) {
        DatumReader<User> reader = new SpecificDatumReader<>(User.class);
        Decoder decoder = null;
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            decoder = DecoderFactory.get()
                .jsonDecoder(User.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (Exception e) {
            throw new RuntimeException("Error when deserializing byte[] to User");
        }
    }
}
