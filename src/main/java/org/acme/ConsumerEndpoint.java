package org.acme;

import org.apache.kafka.common.protocol.types.Field;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class ConsumerEndpoint
{

  @Incoming("topic1")
  public void handle(String d) {
    System.err.println("#Recieved-> "+d);
  }
}
