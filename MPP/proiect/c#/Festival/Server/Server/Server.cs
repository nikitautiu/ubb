using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Repository;
using Festival.Service;
using Festival.Utils;

namespace Festival.Server.Server
{
    public class Server : IServerService
    {
        private ICrudRepository<int, ShowData> ShowRepo;
        private ICrudRepository<int, Purchase> PurchaseRepo;
        private IUserRepo userRepo;

        private List<IClientService> clients;

        public Server(ICrudRepository<int, ShowData> showRepo, ICrudRepository<int, Purchase> purchaseRepo, IUserRepo userRepo)
        {
            this.ShowRepo = showRepo;
            this.PurchaseRepo = purchaseRepo;
            this.userRepo = userRepo;

            clients = new List<IClientService>();
        }

        public void AddPurchase(int showId, string clientName, int quantity)
        {
            PurchaseRepo.save(new Purchase { Id = 0, ShowId = showId, ClientName = clientName, Quantity = quantity });

            var changed = from s in this.ShowRepo.findAll()
                          where s.Id == showId
                          select s;
            this.clients.ForEach(x => x.changesOccured(changed.ToList()));
        }

        public bool Login(string username, string password, IClientService cli)
        {
            var user = userRepo.findByName(username);
            var hash = HashFuncs.CalculateMD5Hash(password);

            if (user != null && user.PassHash == hash)
            {
                this.clients.Add(cli);
                return true;
            }
            return false;
        }



        public IEnumerable<ShowData> GetAll()
        {
            return ShowRepo.findAll();
        }

    }
}
