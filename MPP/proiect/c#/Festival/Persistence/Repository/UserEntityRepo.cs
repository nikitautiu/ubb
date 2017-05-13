using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Repository;

namespace Persistence.Repository
{
    public class UserEntityRepo : IUserRepo
    {
        public Festival.Model.User findOne(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Festival.Model.User> findAll()
        {
            throw new NotImplementedException();
        }

        public void save(Festival.Model.User entity)
        {
            throw new NotImplementedException();
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public void update(Festival.Model.User old, Festival.Model.User entity)
        {
            throw new NotImplementedException();
        }

        public Festival.Model.User findByName(string name)
        {
            using (var db = new DbModel())
            {
      

                // Display all Blogs from the database 
                var query = from u in db.Users
                            where u.Name == name
                            select u;

                if (!query.Any())
                    return null;
                var dbUser = query.First();
                return new Festival.Model.User {Id = dbUser.Id, Name = dbUser.Name, PassHash = dbUser.PassHash};
            }
        }
    }
}
