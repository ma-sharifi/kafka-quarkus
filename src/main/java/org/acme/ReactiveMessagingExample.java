package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.quarkus.runtime.configuration.ConfigUtils;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

//generate --> [ source-a ] --> process --> [processed-a] --> filter --> [ sink-a ] --> consume
@ApplicationScoped
public class ReactiveMessagingExample
{
  {
    System.out.println("#Observes: The application is starting... ." + " Profile is: " + ConfigUtils.getProfiles() );
  }
  //generate --> [ source-a ]
  @Outgoing("source-a")
  public Multi<String> source() {
    return Multi.createFrom().items("hello", "from", "SmallRye", "reactive", "messaging");
  }
  //generate --> [ source-a ]
  @Outgoing("topic2")
  public Multi<String> source1() {
    return Multi.createFrom().items("hello", "from", "SmallRye", "reactive", "messaging");
  }

 //[ source-a ] --> process --> [ processed-a ]
  @Incoming("source-a")
  @Outgoing("processed-a")
  public Message<String> process(Message<String> in) {
    System.out.println("#Process the payload. #Read->manipulate->Write");
    String payload = in.getPayload().toUpperCase();
    // Create a new message from `in` and just update the payload
    return in.withPayload(payload);
  }

  //[ processed-a ] --> filter --> [ sink-a]
  @Incoming("processed-a")
  @Outgoing("sink-a")
  public Multi<String> filter(Multi<String> input) {
    return input.select().where(item -> item.length() > 4);
  }
  //[ sink:processed-b ] --> consume
  @Incoming("sink-a")
  public void sink(String word) {
    System.out.println(">> " + word);
  }

}
