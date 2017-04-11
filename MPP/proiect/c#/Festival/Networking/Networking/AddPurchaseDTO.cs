using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Networking
{
    public class AddPurchaseRequestDTO: DTO
    {
        public int showId { get; set; }
        public string clientName { get; set; }
        public int quantity { get; set; }

        public AddPurchaseRequestDTO(int showId, string clientName, int quantity)
        {
            type = "request";
            name = "addPurchase";
            this.showId = showId;
            this.clientName = clientName;
            this.quantity = quantity;
        }
    }

    public class AddPurchaseResponseDTO : DTO
    {
        public AddPurchaseResponseDTO()
        {
            type = "response";
            name = "addPurchase";
        }
    }
}
