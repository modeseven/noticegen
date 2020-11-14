package org.acme.resteasy;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/booking")
public class ExampleResource {

    @Inject
    NatsListener natsListener;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        int size = natsListener.getMessages().size();
        BookingEvent be = new BookingEvent();

        if (size != 0) {
            be.event =  "message count is:" + size + " last message is:" + natsListener.getMessages().get(size - 1);
        } else {
            be.event =  "No messages yet";
        }
        return  Response.ok(be).build();

    }
}