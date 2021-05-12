using AutoFixture;
using Microsoft.AspNetCore.TestHost;

namespace UnitTests.Configuration
{
    public class TrackerIntegrationTest
    {
        protected TestServer TestServer { get; set; }
        protected IFixture FixtureInstance { get; private set; }

        protected TrackerIntegrationTest()
        {
            FixtureInstance = new Fixture().Customize(new IgnoreVirtualMembersCustomisation());
        }
    }
}