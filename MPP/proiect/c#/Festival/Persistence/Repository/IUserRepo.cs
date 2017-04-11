using System;
using Festival.Model;

namespace Festival.Repository
{
    public interface IUserRepo : ICrudRepository<int, User>
    {
        User findByName(String name);
    }
}
