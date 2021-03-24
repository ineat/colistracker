using AutoMapper;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Reflection;
using Tracker.Configuration;
using Tracker.Infrastructure.Kafka;

namespace Tracker
{
    public class Startup
    {
        public Startup(IConfiguration configuration, IWebHostEnvironment environment)
        {
            Configuration = configuration;
            WebHostEnvironment = environment;
        }

        private IConfiguration Configuration { get; }
        private IWebHostEnvironment WebHostEnvironment { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            services.Configure<AppSettings>(Configuration.GetSection(nameof(AppSettings)));

            AppSettings appSettings = new AppSettings();
            Configuration.GetSection(nameof(AppSettings)).Bind(appSettings);

            Assembly assembly = Assembly.Load(typeof(Startup).Assembly.GetName().Name!);

            services.AddAutoMapper(assembly);
            services.AddHealthChecks();
            services.AddDependencies(appSettings);

            if (!AppSettings.TEST_ENVIRONMENT.Equals(WebHostEnvironment.EnvironmentName, StringComparison.OrdinalIgnoreCase))
            {
                services.AddHostedService<Consumer>();
            }
        }

        public void Configure(IApplicationBuilder app, IMapper mapper)
        {
            mapper.ConfigurationProvider.AssertConfigurationIsValid();

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapHealthChecks("/hc");
            });
        }
    }
}
