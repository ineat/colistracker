using AutoMapper;
using com.ineat.colistracker.historyevent;
using Microsoft.Extensions.Options;
using Refit;
using System.Threading.Tasks;
using Tracker.Configuration;
using Tracker.Models.Dhl;
using Tracker.Repositories;

namespace Tracker.UseCases
{
    public class UpdateCommandConsumer : IUpdateCommandConsumer
    {
        private readonly AppSettings appSettings;
        private readonly IDhlEndpoint iDhlEndpoint;
        private readonly IHistoryEventProducer iHistoryEventProducer;
        private readonly IMapper iMapper;

        public UpdateCommandConsumer(IOptions<AppSettings> appSettings, IDhlEndpoint iDhlEndpoint, IHistoryEventProducer iHistoryEventProducer, IMapper iMapper)
        {
            this.appSettings = appSettings.Value;
            this.iDhlEndpoint = iDhlEndpoint;
            this.iHistoryEventProducer = iHistoryEventProducer;
            this.iMapper = iMapper;
        }

        public async Task Execute(string key, com.ineat.colistracker.updatecommand.Parcel command)
        {
            ListShipmentWrapper? apiResult = null;

            try
            {
                apiResult = await iDhlEndpoint.Track(command.TrackingNumber, appSettings.TrackerConfiguration.ApiKey);
            }
            catch (ApiException apiException)
            {
                if (apiException.StatusCode != System.Net.HttpStatusCode.NotFound)
                {
                    throw;
                }
            }

            Wrapper wrapper = iMapper.Map<Wrapper>(apiResult);
            await iHistoryEventProducer.Execute(key, wrapper);
        }
    }
}
