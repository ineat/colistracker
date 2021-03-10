namespace Tracker.Configuration
{
    public class AppSettings
    {
        public TrackerConfiguration TrackerConfiguration { get; set; }
    }

    public class TrackerConfiguration
    {
        public string Url { get; set; }
        public string ApiKey { get; set; }
    }
}
