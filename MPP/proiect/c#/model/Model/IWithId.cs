using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public interface IWithId<T>
    {
        T Id { get; set; }
    }
}
