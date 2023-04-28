package org.acme.stream;


import org.eclipse.microprofile.reactive.messaging.Channel;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/streams")
public class PlayedMovieResource {

  @Channel("topic-stream")
  Multi<String> records;

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public Multi<String> stream() {
    return records;
  }
}
