package org.acme.resteasy;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resteasy/hello")
public class ExampleResource {

    @Inject
    NatsListener natsListener;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        int size = natsListener.getMessages().size();
        if (size != 0) {
            return "message count is:" + size + " last message is:" + natsListener.getMessages().get(size - 1);
        } else {
            return "No messages yet";
        }

    }
}