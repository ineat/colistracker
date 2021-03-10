using System;

namespace Tracker.Models.Dhl
{
    public class Event
    {
        public DateTime Timestamp { get; set; }
        public Location Location { get; set; }
        public string Description { get; set; }
    }
}
