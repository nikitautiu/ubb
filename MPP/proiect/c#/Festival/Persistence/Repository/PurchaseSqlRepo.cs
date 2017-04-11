using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Model.Validator;

namespace Festival.Repository
{
    public class PurchaseSqlRepo: ICrudRepository<int, Purchase>
    {
        private string filename;

        public PurchaseSqlRepo(string filename)
        {
            this.filename = filename;
        }

        public Purchase findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Purchase> findAll()
        {
            throw new NotImplementedException();
        }

        public void save(Purchase entity)
        {
            Validators.ValidatePurhcase(entity);
            var con = DbUtils.getConnection(filename);
            ValidateEntity(entity, con);

            using (var comm = con.CreateCommand())
            {

                comm.CommandText = "insert into Purchase(showId, clientName, quantity) values(@ShowId, @ClientName, @Quantity)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@ShowId";
                paramId.Value = entity.ShowId;
                comm.Parameters.Add(paramId);

                var paramDesc = comm.CreateParameter();
                paramDesc.ParameterName = "@ClientName";
                paramDesc.Value = entity.ClientName;
                comm.Parameters.Add(paramDesc);

                var paramElems = comm.CreateParameter();
                paramElems.ParameterName = "@Quantity";
                paramElems.Value = entity.Quantity;
                comm.Parameters.Add(paramElems);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No task added !");
            }
        }

        private static void ValidateEntity(Purchase entity, IDbConnection con)
        {
            int sold = 0, total = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "select total(Purchase.quantity) as sold, Show.availableSeats as total from Show left join Purchase on Purchase.showId=Show.id WHERE Show.id=@ShowId";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@ShowId";
                paramId.Value = entity.ShowId;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    
                    if (dataR.Read()) // only second is null if no such show
                    {
                        sold = dataR.IsDBNull(0) ? 0 : (int)dataR.GetDouble(0);
                        total = dataR.GetInt32(1);
                    }
                    else
                    {
                        throw new RepositoryException("Show id not in db");
                    }
                }
            }
            if (entity.Quantity + sold > total)
                throw new RepositoryException("Purchase exceeds total seats");
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public void update(Purchase old, Purchase entity)
        {
            throw new NotImplementedException();
        }
    }
}
