using com.ineat.colistracker.updatecommand;
using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using System;
using System.Threading;
using System.Threading.Tasks;
using Tracker.Configuration;
using Tracker.UseCases;

namespace Tracker.Infrastructure.Kafka
{
    public class Consumer : BackgroundService
    {
        protected readonly AppSettings appSettings;
        private readonly ILogger<Consumer> iLogger;
        private readonly IServiceScopeFactory iServiceScopeFactory;

        public Consumer(IOptions<AppSettings> appSettings, ILogger<Consumer> iLogger, IServiceScopeFactory iServiceScopeFactory)
        {
            this.appSettings = appSettings.Value;
            this.iLogger = iLogger;
            this.iServiceScopeFactory = iServiceScopeFactory;
        }

        public override Task StartAsync(CancellationToken cancellationToken)
        {
#pragma warning disable CS1998 // Async method lacks 'await' operators and will run synchronously
            Task.Factory.StartNew(async () => ExecuteAsync(cancellationToken));
#pragma warning restore CS1998 // Async method lacks 'await' operators and will run synchronously
            return Task.CompletedTask;
        }

        private async Task ProcessMessage(IConsumer<string, Parcel> consumer, ConsumeResult<string, Parcel> consumeResult)
        {
            iLogger.LogInformation($"Received message at {consumeResult.TopicPartitionOffset}: {consumeResult.Message.Value}, with group : {appSettings.KafkaConfiguration.ConsumerGroup}");

            try
            {
                using IServiceScope scope = iServiceScopeFactory.CreateScope();
                await scope.ServiceProvider.GetRequiredService<IUpdateCommandConsumer>().Execute(consumeResult.Message.Value);
            }
            catch (Exception exc)
            {
                iLogger.LogError(exc, "Action execution failed");
            }

            try
            {
                consumer.Commit(consumeResult);
            }
            catch (KafkaException exc)
            {
                iLogger.LogError(exc, $"Commit error: {exc.Error.Reason}");
            }
        }

        private ConsumerConfig BuildConsumerConfig()
        {
            return new ConsumerConfig
            {
                GroupId = appSettings.KafkaConfiguration.ConsumerGroup,
                BootstrapServers = appSettings.KafkaConfiguration.Brokers,
                AutoOffsetReset = AutoOffsetReset.Earliest,
            };
        }
        private SchemaRegistryConfig BuildSchemaRegistryConfig()
        {
            return new SchemaRegistryConfig
            {
                Url = appSettings.KafkaConfiguration.SchemaRegistryUrl
            };
        }

        public override Task StopAsync(CancellationToken cancellationToken) => Task.CompletedTask;

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            using CachedSchemaRegistryClient schemaRegistry = new CachedSchemaRegistryClient(BuildSchemaRegistryConfig());
            using IConsumer<string, Parcel> consumer = new ConsumerBuilder<string, Parcel>(BuildConsumerConfig()).SetValueDeserializer(new AvroDeserializer<Parcel>(schemaRegistry).AsSyncOverAsync())
                                                                                                                 .SetErrorHandler((_, e) => Console.WriteLine($"Error: {e.Reason}"))
                                                                                                                 .Build();
            consumer.Subscribe(appSettings.KafkaConfiguration.UpdateCommandTopic);

            try
            {
                while (!stoppingToken.IsCancellationRequested)
                {
                    try
                    {
                        ConsumeResult<string, Parcel> consumeResult = consumer.Consume(stoppingToken);

                        if (consumeResult.IsPartitionEOF)
                        {
                            iLogger.LogInformation($"Reached end of topic {consumeResult.Topic}, partition {consumeResult.Partition}, offset {consumeResult.Offset}.");

                            continue;
                        }

                        await ProcessMessage(consumer, consumeResult);
                    }
                    catch (ConsumeException exc)
                    {
                        iLogger.LogError(exc, $"Consume error: {exc.Error.Reason}");
                    }
                }
            }
            catch (OperationCanceledException)
            {
                iLogger.LogInformation("Closing consumer.");

                consumer.Close();
            }
        }
    }
}