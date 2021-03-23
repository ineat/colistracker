using Confluent.Kafka;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Tracker.Configuration;

namespace Tracker.UseCases
{
    public class HistoryEventProducer: IHistoryEventProducer
    {
        private readonly AppSettings appSettings;
        private readonly IProducer<int, string> iProducer;
        private readonly ILogger<HistoryEventProducer> iLogger;

        public HistoryEventProducer(IOptions<AppSettings> appSettings, IProducer<int, string> iProducer, ILogger<HistoryEventProducer> iLogger)
        {
            this.appSettings = appSettings.Value;
            this.iProducer = iProducer;
            this.iLogger = iLogger;
        }
    }
}
