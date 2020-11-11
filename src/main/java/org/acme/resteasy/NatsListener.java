package org.acme.resteasy;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;

@Startup
@ApplicationScoped
public class NatsListener {
    private static final Logger LOG = Logger.getLogger("NatsListener");
    private Connection nats;

    @ConfigProperty(name = "nats.host", defaultValue = "localhost")
    public String host;

    @ConfigProperty(name = "nats.port", defaultValue = "4222")
    public String port;

    @ConfigProperty(name = "nats.subject", defaultValue = "subject")
    public String subject;

    public List<String> messages = new ArrayList<String>();

    void onStart(@Observes StartupEvent ev) {
        String natsConStr = "nats://" + host + ":" + port;
        LOG.info("starting Nats Listener, connecting to nats on:" + natsConStr);
        // connecting to nats on:nats://nats:tcp://10.106.107.144:4223

        try {
            nats = Nats.connect(host);
            LOG.info("connected to NATS on:" + natsConStr);

            Dispatcher d = nats.createDispatcher((msg) -> {
                String response = new String(msg.getData(), StandardCharsets.UTF_8);
                LOG.info("Recieved Nats message:" + response);
                messages.add(response);  
            });

            d.subscribe(subject);
            LOG.info("subscribed to subject:" + subject);


        } catch (Exception e) {
            LOG.error("failed to connect nats on:" + natsConStr);
            e.printStackTrace();
        }
    }

    public  List<String> getMessages() {
        return this.messages;
    }

}
