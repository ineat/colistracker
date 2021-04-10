using com.ineat.colistracker.historyevent;
using System.Threading.Tasks;

namespace Tracker.UseCases
{
    public interface IHistoryEventProducer
    {
        Task Execute(string key, Wrapper wrapper);
    }
}
