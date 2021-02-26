package com.ineat.colistracker.router;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.quarkus.arc.DefaultBean;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class SchemaRegistryClientProducer {

  private static final int MAX_NUM_OF_SCHEMAS = 10;

  @ConfigProperty(name = "quarkus.kafka-streams.schema-registry-url")
  String schemaRegistryUrl;

  @Produces
  @DefaultBean
  public SchemaRegistryClient schemaRegistryClient() {
    return new CachedSchemaRegistryClient(this.schemaRegistryUrl, MAX_NUM_OF_SCHEMAS);
  }
}