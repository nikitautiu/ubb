using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Newtonsoft.Json;

namespace Festival.Networking
{
    public class ChangesOccuredDTO: DTO
    {
        public List<ShowDataDTO> shows { get; set; }

        public ChangesOccuredDTO(IEnumerable<ShowData> shows)
        {
            type = "update";
            name = "changesOccurred";
            this.shows = new List<ShowDataDTO>();
            foreach (var change in shows)
            {
                this.shows.Add(new ShowDataDTO(change));
            }
        }

        [JsonIgnore]
        public List<ShowData> modelShow
        {
            get
            {
                var sh = new List<ShowData>();
                foreach (var show in this.shows)
                {
                    sh.Add(new ShowData()
                    {
                        Id = show.Id,
                        ArtistName = show.ArtistName,
                        LocationName = show.LocationName,
                        StartTime = show.StartTime,
                        RemainingSeats = show.RemainingSeats
                    });
                }
                return sh;
            }
        }

    }

    public class GetAllResponseDTO : DTO
    {
        public List<ShowDataDTO> shows { get; set; }

        [JsonIgnore]
        public List<ShowData> modelShow
        {
            get
            {
                var sh = new List<ShowData>();
                foreach (var show in this.shows)
                {
                    sh.Add(new ShowData()
                    {
                        Id = show.Id, ArtistName = show.ArtistName,
                        LocationName = show.LocationName, StartTime = show.StartTime,
                        RemainingSeats = show.RemainingSeats
                    });
                }
                return sh;
            }
        }

        public GetAllResponseDTO(IEnumerable<ShowData> shows)
        {
            type = "response";
            name = "getAll";
            this.shows = new List<ShowDataDTO>();
            foreach (var change in shows)
            {
                this.shows.Add(new ShowDataDTO(change));
            }
        }
    }

    public class GetAllRequestDTO : DTO
    {
        public GetAllRequestDTO()
        {
            type = "request";
            name = "getAll";
        }
    }

    public class ShowDataDTO
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        [JsonProperty("artistName")]
        public string ArtistName { get; set; }
        [JsonProperty("locationName")]
        public string LocationName { get; set; }
        [JsonProperty("startTime")]
        public DateTime StartTime { get; set; }
        [JsonProperty("remainingSeats")]
        public int RemainingSeats { get; set; }

        public ShowDataDTO(ShowData sd)
        {
            Id = sd.Id;
            ArtistName = sd.ArtistName;
            LocationName = sd.LocationName;
            StartTime = sd.StartTime;
            RemainingSeats = sd.RemainingSeats;
        }
    }
}
