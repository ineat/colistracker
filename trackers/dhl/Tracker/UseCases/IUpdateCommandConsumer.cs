using com.ineat.colistracker.updatecommand;
using System.Threading.Tasks;

namespace Tracker.UseCases
{
    public interface IUpdateCommandConsumer
    {
        Task Execute(Parcel command);
    }
}
