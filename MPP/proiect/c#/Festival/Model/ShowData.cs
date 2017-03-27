using System;

namespace Festival.Model
{
    public class ShowData: IWithId<int>
    {
        public int Id { get; set; }
        public string ArtistName { get; set;  }
        public string LocationName { get; set; }
        public  DateTime StartTime { get; set; }
        public int RemainingSeats { get; set; }
    }
}