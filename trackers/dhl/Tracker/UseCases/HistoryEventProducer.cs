using AutoMapper;
using Confluent.Kafka;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using System;
using System.Threading.Tasks;
using Tracker.Configuration;
using Tracker.Models.Dhl;
using Tracker.Models.Kafka;

namespace Tracker.UseCases
{
    public class HistoryEventProducer : IHistoryEventProducer
    {
        private readonly AppSettings appSettings;
        private readonly IProducer<string, string> iProducer;
        private readonly ILogger<HistoryEventProducer> iLogger;
        private readonly IMapper iMapper;

        public HistoryEventProducer(IOptions<AppSettings> appSettings, IProducer<string, string> iProducer, ILogger<HistoryEventProducer> iLogger, IMapper iMapper)
        {
            this.appSettings = appSettings.Value;
            this.iProducer = iProducer;
            this.iLogger = iLogger;
            this.iMapper = iMapper;
        }

        public async Task Execute(string trackingNumber, ListShipmentWrapper? wrapper)
        {
            //HistoryEvent historyEvent = iMapper.Map<HistoryEvent>((trackingNumber, wrapper));

            HistoryEvent historyEvent = new HistoryEvent { TrackingNumber = trackingNumber, Success = wrapper != null };

            await SendMessage(trackingNumber, historyEvent);
        }

        private async Task SendMessage(string trackingNumber, HistoryEvent historyEvent)
        {
            try
            {
                DeliveryResult<string, string> deliveryResult = await iProducer.ProduceAsync(appSettings.KafkaConfiguration.HistoryEventTopic, new Message<string, string> { Key = trackingNumber, Value = JsonConvert.SerializeObject(historyEvent) });

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
