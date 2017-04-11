using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model
{
    public class Purchase: IWithId<int>
    {
        public int Id { get; set; }
        public int ShowId { get; set; }
        public string ClientName { get; set; }
        public int Quantity { get; set; }
    }
}
