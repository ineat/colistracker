using AutoMapper;

namespace UnitTests.Configuration
{
    public class MapperConfigurationTest
    {
        protected readonly IMapper iMapper;

        protected MapperConfigurationTest()
        {
            MapperConfiguration config = new MapperConfiguration(
                cfg => cfg.AddMaps("Tracker")
                );
            config.AssertConfigurationIsValid();
            iMapper = config.CreateMapper();
        }
    }
}
