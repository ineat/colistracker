package com.ineat.colistracker.router;

import com.ineat.colistracker.avro.Courier;
import com.ineat.colistracker.avro.TrackingAskUpdateEvent;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class TopologyProducer {

    private final SchemaRegistryClient schemaRegistryClient;

    @Inject
    public TopologyProducer(SchemaRegistryClient schemaRegistryClient) {
        this.schemaRegistryClient = schemaRegistryClient;
    }

    @Produces
    public Topology buildTopology() {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        final Serde<TrackingAskUpdateEvent> avroSerde = new SpecificAvroSerde<>(schemaRegistryClient);

        final KStream<Long, TrackingAskUpdateEvent> events = streamsBuilder.stream(KafkaTopics.GENERIC_UPDATE_COMMAND,
                Consumed.with(Serdes.Long(), avroSerde));

        final int laPoste = 0;
        final int dhl = 1;

        final KStream<Long, TrackingAskUpdateEvent>[] routing = events.branch(
                (id, event) -> event.getCourier() == Courier.LA_POSTE,
                (id, event) -> event.getCourier() == Courier.DHL
        );

        routing[laPoste].to(KafkaTopics.LA_POSTE_UPDATE_COMMAND);
        routing[dhl].to(KafkaTopics.DHL_GENERIC_UPDATE_COMMAND);

        return streamsBuilder.build();
    }
}
