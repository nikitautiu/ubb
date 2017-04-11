using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;

namespace Festival.Repository
{
    public class ShowDataSqlRepo: ICrudRepository<int, ShowData>
    {
        private string filename;

        public ShowDataSqlRepo(string filename)
        {
            this.filename = filename;
        }

        public ShowData findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ShowData> findAll()
        {
            IDbConnection con = DbUtils.getConnection(filename);
            IList<ShowData> shows = new List<ShowData>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select s.id as id, a.name as artistName,  " +
                    "l.name as locationName, datetime(s.startTime) as startTime,  " +
                    "cast(total(p.quantity) as integer) as soldSeats, s.availableSeats as availableSeats " +
                    "from  Show as s  join Artist as a on a.id = s.artistId join Location as l " +
                    "on l.id = s.locationId left join Purchase as p on p.showId = s.id group by s.id;";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string artistName = dataR.GetString(1);
                        string locationName = dataR.GetString(2);
                        DateTime startTime = dataR.GetDateTime(3);
                        int remainingSeats = dataR.GetInt32(5) - dataR.GetInt32(4);

                        shows.Add(new ShowData
                        {
                            Id = id,
                            ArtistName = artistName,
                            LocationName = locationName,
                            StartTime = startTime,
                            RemainingSeats = remainingSeats
                        });
                    }
                }
            }

            return shows;
        }

        public void save(ShowData entity)
        {
            throw new NotImplementedException();
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public void update(ShowData old, ShowData entity)
        {
            throw new NotImplementedException();
        }
    }
}
