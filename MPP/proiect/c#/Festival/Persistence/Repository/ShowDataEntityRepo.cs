using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Repository;

namespace Persistence.Repository
{
    public class ShowDataEntityRepo : ICrudRepository<int, Festival.Model.ShowData>
    {
        public ShowData findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ShowData> findAll()
        {
            using (var db = new DbModel())
            {
                var val = (from s in db.Shows
                    join a in db.Artists on s.ArtistId equals a.Id
                    join l in db.Locations on s.LocationId equals l.Id
                    from p in db.Purchases.Where(p => p.ShowId == s.Id).DefaultIfEmpty()
                    group new {s, a, l, p} by p.ShowId
                    into tick
                    select new ShowData
                    {
                        Id = tick.FirstOrDefault().s.Id,
                        ArtistName = tick.FirstOrDefault().a.Name,
                        LocationName = tick.FirstOrDefault().l.Name,
                        StartTime = tick.FirstOrDefault().s.StartTime,
                        RemainingSeats = tick.FirstOrDefault().s.AvailableSeats - tick.Sum(p => p.p.Quanitity)
                    }).ToList();
                return val;
            }
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
