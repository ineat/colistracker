using System.Threading.Tasks;
using Tracker.Models.Kafka;

namespace Tracker.UseCases
{
    public interface IUpdateCommandConsumer
    {
        Task Execute(UpdateCommand command);
    }
}
