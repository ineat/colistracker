using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.Configuration;
using Tracker;
using Tracker.Configuration;

namespace UnitTests.Configuration
{
    public static class HostConfiguration
    {
        public static IWebHostBuilder OfflineValidHostBuilder()
        {
            return new WebHostBuilder()
                .UseContentRoot(".")
                .UseEnvironment(AppSettings.TEST_ENVIRONMENT)
                .ConfigureAppConfiguration((builderContext, config) =>
                {
                    config.AddJsonFile("appsettings.json", optional: false, reloadOnChange: false);
                })
                .ConfigureServices(services =>
                {
                    // TODO
                })
                .ConfigureTestServices(services =>
                {
                    // APRES le ConfigureServices du Startup
                })
                .UseStartup<Startup>();
        }
    }
}
