using com.ineat.colistracker.updatecommand;
using System.Threading.Tasks;

namespace Tracker.UseCases
{
    public interface IUpdateCommandConsumer
    {
        Task Execute(string key, Parcel command);
    }
}
