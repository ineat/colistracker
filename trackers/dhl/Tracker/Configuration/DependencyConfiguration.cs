using Microsoft.Extensions.DependencyInjection;
using Refit;
using System;
using Tracker.Repositories;
using Tracker.UseCases;

namespace Tracker.Configuration
{
    public static class DependencyConfiguration
    {
        public static IServiceCollection AddDependencies(this IServiceCollection services, AppSettings appSettings)
        {
            #region Kafka
            services.AddTransient(services => new Confluent.Kafka.ProducerBuilder<string, string>(new Confluent.Kafka.ProducerConfig { BootstrapServers = appSettings.KafkaConfiguration.Brokers }).Build());
            #endregion

            #region Consumer
            services.AddTransient<IUpdateCommandConsumer, UpdateCommandConsumer>();
            services.AddTransient<IHistoryEventProducer, HistoryEventProducer>();
            #endregion

            #region Tracker
            services.AddRefitClient<IDhlEndpoint>().ConfigureHttpClient(c => { c.BaseAddress = new Uri(appSettings.TrackerConfiguration.Url); });
            #endregion

            return services;
        }
    }
}
