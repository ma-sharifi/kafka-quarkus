package org.acme;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("kafka")
public class ProducerService
{

  //https://smallrye.io/smallrye-reactive-messaging/4.5.0/concepts/emitter/#overflow-management
  // Set the max size to 256 and fail if reached
  @OnOverflow(value = OnOverflow.Strategy.BUFFER, bufferSize = 256)
  @Channel("topic1")
  Emitter<String> channel1;

  static AtomicInteger counter=new AtomicInteger(0);

  @Produces(MediaType.TEXT_PLAIN)
  @GET
  public String produce(){
    String                counterString = "#Message-"+counter.incrementAndGet();
    CompletionStage<Void> ack           = channel1.send(counterString);

    return counterString;
  }

}
