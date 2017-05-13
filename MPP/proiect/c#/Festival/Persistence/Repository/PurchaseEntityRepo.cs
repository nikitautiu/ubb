using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Repository;

namespace Persistence.Repository
{
    public class PruchaseEntityRepo : ICrudRepository<int, Festival.Model.Purchase>
    {
        public Festival.Model.Purchase findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Festival.Model.Purchase> findAll()
        {
            throw new NotImplementedException();
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public void update(Festival.Model.Purchase old, Festival.Model.Purchase entity)
        {
            throw new NotImplementedException();
        }

        public void save(Festival.Model.Purchase entity)
        {
            using (var db = new DbModel())
            {
                Purchase newPurchase = new Purchase {ClientName = entity.ClientName, Quanitity = entity.Quantity, ShowId = entity.ShowId};
                db.Purchases.Add(newPurchase);
                db.SaveChanges();
            }
        }

    }
}
