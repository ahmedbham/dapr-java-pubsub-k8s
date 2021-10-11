package com.example.dapr.consumer.demodaprconsumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class InputBindingController {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
   * Handles a registered publish endpoint on this app.
   * @param cloudEvent The cloud event received.
   * @return A message containing the time.
   */
    @Topic(name = "sample123", pubsubName = "messagebus")
    @PostMapping(path = "/sample123")
    public Mono<Void> handleInputBinding(@RequestBody(required = false) CloudEvent<String> body) {
        return Mono.fromRunnable(() -> {
            try {
                // CloudEvent envelope = SERIALIZER.deserialize(body, CloudEventEnvelope.class);

                // String message = envelope.getData() == null ? "" : envelope.getData();
                // System.out.println("Subscriber got message: " + message);
              // System.out.println("Subscriber got: " + OBJECT_MAPPER.writeValueAsString(data));
              // System.out.println("Subscriber got: " + OBJECT_MAPPER.writeValueAsString(cloudEvent));
              System.out.println("Received message through binding: " + (body == null ? "" : body.getData()));
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });
        // return Mono.fromRunnable(() ->
        // System.out.println("Received message through binding: " + (body == null ? "" : new String(body))));
    }
}
