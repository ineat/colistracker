package com.ineat.colistracker.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class RequestOkapiKeyHeaderFactory implements ClientHeadersFactory {
    @Inject
    @ConfigProperty(name = "laposte.api.key")
    String apiKey;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> multivaluedMap, MultivaluedMap<String, String> multivaluedMap1) {
        MultivaluedMap<String, String> result = new MultivaluedMapImpl<>();
        result.add("X-Okapi-Key", apiKey);
        return result;
    }
}
