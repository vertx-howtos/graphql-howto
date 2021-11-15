package io.vertx.howtos.graphql;

import java.util.UUID;

public record Task(String id, String description, boolean completed) {

  public Task(String description) {
    this(UUID.randomUUID().toString(), description, false); // <1>
  }
}
