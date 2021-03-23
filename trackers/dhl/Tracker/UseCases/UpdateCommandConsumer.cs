using System;
using System.Threading.Tasks;
using Tracker.Infrastructure.Kafka;
using Tracker.Models.Kafka;
using Tracker.Repositories;

namespace Tracker.UseCases
{
    public class UpdateCommandConsumer : IUpdateCommandConsumer
    {
        private readonly IDhlEndpoint iDhlEndpoint;

        public UpdateCommandConsumer(IDhlEndpoint iDhlEndpoint)
        {
            this.iDhlEndpoint = iDhlEndpoint;
        }

        public Task Execute(UpdateCommand command)
        {
            throw new NotImplementedException();
        }
    }
}
