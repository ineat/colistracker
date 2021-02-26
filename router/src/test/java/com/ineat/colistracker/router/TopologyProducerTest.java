package com.ineat.colistracker.router;


import com.ineat.colistracker.avro.Courier;
import com.ineat.colistracker.avro.TrackingAskUpdateEvent;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@QuarkusTest
public class TopologyProducerTest {

    Topology topology;

    TopologyTestDriver testDriver;

    TestInputTopic<Long, TrackingAskUpdateEvent> inputTopic;
    TestOutputTopic<Long, TrackingAskUpdateEvent> outputTopic;

    Serde<TrackingAskUpdateEvent> avroSerde;

    @BeforeEach
    public void setup() {
        final MockSchemaRegistryClient client = new MockSchemaRegistryClient();
        topology = new TopologyProducer(client).buildTopology();

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");

        testDriver = new TopologyTestDriver(topology, props);

        avroSerde = new SpecificAvroSerde<>(client);
        avroSerde.configure(Map.of(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://dummy"), false);

        inputTopic = testDriver.createInputTopic(KafkaTopics.GENERIC_UPDATE_COMMAND, Serdes.Long().serializer(), avroSerde.serializer());
        outputTopic = testDriver.createOutputTopic(KafkaTopics.LA_POSTE_UPDATE_COMMAND, Serdes.Long().deserializer(), avroSerde.deserializer());
    }

    @AfterEach
    public void tearDown() {
        avroSerde.close();
        testDriver.close();
    }

    @Test
    @DisplayName("Given la poste command event, should pipe to laposte topic")
    public void laPosteTest() {
        // ARRANGE
        final TrackingAskUpdateEvent trackingAskUpdateEvent = new TrackingAskUpdateEvent("1L3849", "59000", Courier.LA_POSTE);

        // ACT
        inputTopic.pipeInput(1L, trackingAskUpdateEvent);

        // ASSERT
        final TrackingAskUpdateEvent res = outputTopic.readValue();
        assertThat(res).isNotNull();

    }
}