using System.Threading.Tasks;
using Tracker.Models.Dhl;

namespace Tracker.UseCases
{
    public interface IHistoryEventProducer
    {
        Task Execute(string trackingNumber, ListShipmentWrapper? wrapper);
    }
}
