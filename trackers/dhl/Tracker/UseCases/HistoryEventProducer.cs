using com.ineat.colistracker.historyevent;
using Confluent.Kafka;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Tracker.Configuration;

namespace Tracker.UseCases
{
    public class HistoryEventProducer : IHistoryEventProducer
    {
        private readonly KafkaConfiguration configuration;
        private readonly ILogger<HistoryEventProducer> iLogger;

        public HistoryEventProducer(IOptions<AppSettings> appSettings, ILogger<HistoryEventProducer> iLogger)
        {
            configuration = appSettings.Value.KafkaConfiguration;
            this.iLogger = iLogger;
        }

        public async Task Execute(string key, Wrapper wrapper)
        {
            try
            {


                wrapper = new Wrapper { };
                wrapper.Destination = new Address { City = "Munich", CountryCode = "FR", Localisation = "30 rue du poivre", ZipCode = "59115" };
                wrapper.Details = new Details { Height = new UnitValue { Unit = "cm", Value = 10 }, Width = new UnitValue { Unit = "cm", Value = 10 }, Weight = new UnitValue { Unit = "kg", Value = 10 } };
                wrapper.Events = new List<Event> { new Event { Description = "lipsum", Status = "pre-transit", Timestamp = 1620823407237 } };
                wrapper.Origin = new Address { City = "Munich", CountryCode = "FR", Localisation = "30 rue du poivre", ZipCode = "59115" };
                wrapper.Parcel = new Parcel { Carrier = Carrier.DHL, TrackingNumber = "00340434292135100124" };

                using CachedSchemaRegistryClient schemaRegistry = new CachedSchemaRegistryClient(new SchemaRegistryConfig { Url = configuration.SchemaRegistryUrl });
                using IProducer<string, Wrapper> iProducer = new ProducerBuilder<string, Wrapper>(new ProducerConfig { BootstrapServers = configuration.Brokers }).SetValueSerializer(new AvroSerializer<Wrapper>(schemaRegistry))
                                                                                                                                                                  .Build();
                DeliveryResult<string, Wrapper> deliveryResult = await iProducer.ProduceAsync(configuration.HistoryEventTopic, new Message<string, Wrapper> { Key = key, Value = wrapper });
                iLogger.LogInformation($"Delivered '{deliveryResult.Value}' to '{deliveryResult.TopicPartitionOffset}'");
            }
            catch (ProduceException<int, string> exception)
            {
                iLogger.LogError(exception, $"Delivery failed: {exception.Error.Reason}");
            }
            catch (Exception exception)
            {
                iLogger.LogError(exception, $"Delivery failed: {exception.Message}");
            }
        }
    }
}
