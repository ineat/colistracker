namespace Tracker.Models.Kafka
{
    public class HistoryEvent
    {
        public string TrackingNumber { get; set; }
        public bool Success { get; set; }
    }
}
