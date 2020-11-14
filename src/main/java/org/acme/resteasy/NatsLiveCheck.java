package org.acme.resteasy;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import org.jboss.logging.Logger;

@Liveness
@ApplicationScoped
public class NatsLiveCheck implements HealthCheck {

    private static final Logger LOG = Logger.getLogger("NatsLiveCheck");

    @Inject
    NatsListener nats;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Nats connection health check");
        if (nats.isLive()) {
            responseBuilder.up();
        } else {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }

}
