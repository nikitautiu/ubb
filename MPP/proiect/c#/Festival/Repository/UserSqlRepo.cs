using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;

namespace Festival.Repository
{
    public class UserSqlRepo : IUserRepo
    {
        private readonly string filename;

        public UserSqlRepo(string filename)
        {
            this.filename = filename;
        }
        public User findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<User> findAll()
        {
            throw new NotImplementedException();
        }

        public void save(User entity)
        {
            throw new NotImplementedException();
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public void update(User old, User entity)
        {
            throw new NotImplementedException();
        }

        public User findByName(string name)
        {
            IDbConnection con = DbUtils.getConnection(filename);
            IList<ShowData> shows = new List<ShowData>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from User where name=@Name";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@Name";
                paramId.Value = name;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (!dataR.Read()) return null;
                    var id = dataR.GetInt32(0);
                    var username = dataR.GetString(1);
                    var passHash = dataR.GetString(2);

                    return new User {Id = id, Name = name, PassHash = passHash};
                }
            }
            return null;
        }
    }
}
