namespace Tracker.Configuration
{
    public class AppSettings
    {
        public const string TEST_ENVIRONMENT = "test";
        public TrackerConfiguration TrackerConfiguration { get; set; }
        public KafkaConfiguration KafkaConfiguration { get; set; }
    }

    public class KafkaConfiguration
    {
        public string Brokers { get; set; }
        public string SchemaRegistryUrl { get; set; }
        public string ConsumerGroup { get; set; }
        public string UpdateCommandTopic { get; set; }
        public string HistoryEventTopic { get; set; }
    }

    public class TrackerConfiguration
    {
        public string Url { get; set; }
        public string ApiKey { get; set; }
    }
}
