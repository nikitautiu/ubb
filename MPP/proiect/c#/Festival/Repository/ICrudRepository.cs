using System;
using System.Collections.Generic;
using Festival.Model;

namespace Festival.Repository
{
    public class RepositoryException : ApplicationException
    {
        public RepositoryException() { }
        public RepositoryException(String mess) : base(mess) { }
        public RepositoryException(String mess, Exception e) : base(mess, e) { }
    }

    public interface ICrudRepository<ID, E> where E:IWithId<ID>
    {
        E findOne(ID id);
        IEnumerable<E> findAll();
        void save(E entity);
        void delete(ID id);
        void update(E old, E entity);
    }
}
