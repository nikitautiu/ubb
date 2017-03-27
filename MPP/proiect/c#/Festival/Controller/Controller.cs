using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Repository;

namespace Festival.Controller
{
    
    public class Controller
    {
        private ICrudRepository<int, ShowData> ShowRepo;
        private ICrudRepository<int, Purchase> PurchaseRepo;
        private IUserRepo userRepo;
        public IBindingList ShowBindingList { get; }

        public Controller(ICrudRepository<int, ShowData> showRepo, ICrudRepository<int, Purchase> purchaseRepo, IUserRepo userRepo)
        {
            this.ShowRepo = showRepo;
            this.PurchaseRepo = purchaseRepo;
            this.userRepo = userRepo;
            ShowBindingList = new BindingList<ShowData>();

            RefreshList();
        }

        private void RefreshList()
        {
            ShowBindingList.Clear();
            foreach (var showData in ShowRepo.findAll())
            {
                ShowBindingList.Add(showData);
            }
        }

        public void AddPurchase(int showId, string clientName, int quantity)
        {
            PurchaseRepo.save(new Purchase {Id = 0, ShowId = showId, ClientName = clientName, Quantity = quantity});
            RefreshList();
        }

        public bool Login(string username, string password)
        {
            var user = userRepo.findByName(username);
            var hash = CalculateMD5Hash(password);

            return user != null && user.PassHash == hash;
        }

        private string CalculateMD5Hash(string input)
        {
            MD5 md5 = System.Security.Cryptography.MD5.Create();
            byte[] inputBytes = System.Text.Encoding.ASCII.GetBytes(input);
            byte[] hash = md5.ComputeHash(inputBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.Length; i++)
            {
                sb.Append(hash[i].ToString("x2")); // to lowercase hex
            }
            return sb.ToString();

        }

    }
}
