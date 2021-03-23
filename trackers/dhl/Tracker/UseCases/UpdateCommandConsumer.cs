using Microsoft.Extensions.Options;
using Refit;
using System.Threading.Tasks;
using Tracker.Configuration;
using Tracker.Models.Dhl;
using Tracker.Models.Kafka;
using Tracker.Repositories;

namespace Tracker.UseCases
{
    public class UpdateCommandConsumer : IUpdateCommandConsumer
    {
        private readonly AppSettings appSettings;
        private readonly IDhlEndpoint iDhlEndpoint;
        private readonly IHistoryEventProducer iHistoryEventProducer;

        public UpdateCommandConsumer(IOptions<AppSettings> appSettings, IDhlEndpoint iDhlEndpoint, IHistoryEventProducer iHistoryEventProducer)
        {
            this.appSettings = appSettings.Value;
            this.iDhlEndpoint = iDhlEndpoint;
            this.iHistoryEventProducer = iHistoryEventProducer;
        }

        public async Task Execute(UpdateCommand command)
        {
            ListShipmentWrapper? wrapper = null;

            try
            {
                wrapper = await iDhlEndpoint.Track(command.TrackingNumber, appSettings.TrackerConfiguration.ApiKey);
            }
            catch (ApiException apiException)
            {
                if (apiException.StatusCode != System.Net.HttpStatusCode.NotFound)
                {
                    throw;
                }
            }

            await iHistoryEventProducer.Execute(command.TrackingNumber, wrapper);
        }
    }
}
