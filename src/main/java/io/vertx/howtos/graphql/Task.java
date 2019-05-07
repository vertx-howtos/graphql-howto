package io.vertx.howtos.graphql;

import java.util.UUID;

public class Task {

  public String id;
  public String description;
  public boolean completed;

  public Task(String description) {
    id = UUID.randomUUID().toString(); // <1>
    this.description = description;
  }
}
