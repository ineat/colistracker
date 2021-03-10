using Refit;
using System.Threading.Tasks;
using Tracker.Models.Dhl;

namespace Tracker.Repositories
{
    public interface IDhlEndpoint
    {
        [Get("/track/shipments?trackingNumber={trackingNumber}")]
        Task<ListShipmentWrapper> Track(string trackingNumber, [Header("DHL-API-Key")] string apiKey);
    }
}
