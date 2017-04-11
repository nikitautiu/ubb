using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text;
using Festival.Model;
using Festival.Repository;
using Festival.Service;
using Festival.Utils;

namespace Festival.Standalone
{
    public class StandaloneService : IServerService
    {
        private ICrudRepository<int, ShowData> ShowRepo;
        private ICrudRepository<int, Purchase> PurchaseRepo;
        private IUserRepo userRepo;
        private IClientService cli;

        public StandaloneService(ICrudRepository<int, ShowData> showRepo, ICrudRepository<int, Purchase> purchaseRepo, IUserRepo userRepo)
        {
            this.ShowRepo = showRepo;
            this.PurchaseRepo = purchaseRepo;
            this.userRepo = userRepo;
        }


        public void AddPurchase(int showId, string clientName, int quantity)
        {
            PurchaseRepo.save(new Purchase { Id = 0, ShowId = showId, ClientName = clientName, Quantity = quantity });

            var changed = from s in this.ShowRepo.findAll()
            where s.Id == showId select s;
            this.cli.changesOccured(changed.ToList());
        }

        public bool Login(string username, string password, IClientService cli)
        {
            var user = userRepo.findByName(username);
            var hash = HashFuncs.CalculateMD5Hash(password);

            if (user != null && user.PassHash == hash)
            {
                this.cli = cli;
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