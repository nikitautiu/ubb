using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class User : IWithId<int>
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string PassHash { get; set; }
    }
}
