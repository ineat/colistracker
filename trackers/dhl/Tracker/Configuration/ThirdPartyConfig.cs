using Microsoft.Extensions.DependencyInjection;
using Refit;
using System;
using Tracker.Repositories;

namespace Tracker.Configuration
{
    public static class ThirdPartyConfig
    {
        public static IServiceCollection AddThirdParties(this IServiceCollection services, AppSettings appSettings)
        {
            #region DHL
            services.AddRefitClient<IDhlEndpoint>().ConfigureHttpClient(c => { c.BaseAddress = new Uri(appSettings.TrackerConfiguration.Url); });
            #endregion

            return services;
        }
    }
}
