namespace Tracker.Configuration
{
    public class AppSettings
    {
        public TrackerConfiguration TrackerConfiguration { get; set; }
        public KafkaConfiguration KafkaConfiguration { get; set; }
    }

    public class KafkaConfiguration
    {
        public string Brokers { get; set; }
        public string ConsumerGroup { get; set; }
        public string UpdateCommandTopic { get; set; }
        public string HistoryTopic { get; set; }
    }

    public class TrackerConfiguration
    {
        public string Url { get; set; }
        public string ApiKey { get; set; }
    }
}
