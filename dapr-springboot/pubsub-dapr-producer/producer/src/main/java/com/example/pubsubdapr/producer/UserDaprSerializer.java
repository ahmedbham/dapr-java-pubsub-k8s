package com.example.pubsubdapr.producer;

import io.dapr.serializer.DaprObjectSerializer;
import io.dapr.utils.TypeRef;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserDaprSerializer implements DaprObjectSerializer {
    /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(Object o) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
        oos.writeObject(o);
        oos.flush();
        return bos.toByteArray();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <User> User deserialize(byte[] data, TypeRef<User> type) throws IOException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
      try (ObjectInputStream ois = new ObjectInputStream(bis)) {
        try {
          return (User) ois.readObject();
        } catch (Exception e) {
          throw new IOException("Could not deserialize Java object.", e);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentType() {
    return "application/json";
  }
}
