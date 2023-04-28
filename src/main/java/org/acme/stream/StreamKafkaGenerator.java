package org.acme.stream;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StreamKafkaGenerator
{
  private Random random = new Random();

  @Outgoing("topic-stream")
  public Multi<String> generate() {
    return Multi.createFrom().ticks().every(Duration.ofMillis(1000))
        .onOverflow().drop()
        .map(tick -> {
          long time = Instant.now().getEpochSecond();
          // Region as key
          return "#Message: "+time;
        });
  }
}
