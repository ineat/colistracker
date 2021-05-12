using System.Collections.Generic;

namespace Tracker.Models.Dhl
{
    public class Shipment
    {
        public string Id { get; set; }
        public string Service { get; set; }
        public Location Origin { get; set; }
        public Location Destination { get; set; }
        public Event Status { get; set; }
        public Details Details { get; set; }
        public List<Event> Events { get; set; }
    }
}
