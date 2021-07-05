package com.ineat.colistracker;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;
import java.util.Map;

public class IntegrationTestLifecycleManager implements QuarkusTestResourceLifecycleManager {


    @Override
    public Map<String, String> start() {
        final Map<String, String> props1 = InMemoryConnector.switchIncomingChannelsToInMemory("tracking-events");

        // Schema registry

        // Postgresql

        return new HashMap<>(props1);
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}