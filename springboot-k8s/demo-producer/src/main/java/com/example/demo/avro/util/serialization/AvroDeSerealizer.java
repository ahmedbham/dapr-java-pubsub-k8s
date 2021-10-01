package com.example.demo.avro.util.serialization;


import java.io.IOException;
import java.util.Map;

import com.example.demo.avro.model.AvroHttpRequest;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.common.errors.SerializationException;

public class AvroDeSerealizer implements org.apache.kafka.common.serialization.Deserializer<AvroHttpRequest> {
    private static Logger logger = LoggerFactory.getLogger(AvroDeSerealizer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public AvroHttpRequest deserialize(String topic, byte[] data) {
        DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = null;
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            decoder = DecoderFactory.get()
                .jsonDecoder(AvroHttpRequest.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to AvroHttpRequest");
        }
    }

    @Override
    public void close() {
    }

    // public AvroHttpRequest deSerealizeAvroHttpRequestJSON(byte[] data) {
    //     DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
    //     Decoder decoder = null;
    //     try {
    //         decoder = DecoderFactory.get()
    //             .jsonDecoder(AvroHttpRequest.getClassSchema(), new String(data));
    //         return reader.read(null, decoder);
    //     } catch (IOException e) {
    //         logger.error("Deserialization error" + e.getMessage());
    //     }
    //     return null;
    // }

    // public AvroHttpRequest deSerealizeAvroHttpRequestBinary(byte[] data) {
    //     DatumReader<AvroHttpRequest> employeeReader = new SpecificDatumReader<>(AvroHttpRequest.class);
    //     Decoder decoder = DecoderFactory.get()
    //         .binaryDecoder(data, null);
    //     try {
    //         return employeeReader.read(null, decoder);
    //     } catch (IOException e) {
    //         logger.error("Deserialization error" + e.getMessage());
    //     }
    //     return null;
    // }    
}
