using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Repository;
using Festival.Service;

namespace Festival.Controller
{
    
    public class Controller
    {
        private IService serv;
        public IBindingList ShowBindingList { get; }

        public Controller(IService serv)
        {
            this.serv = serv;
            this.serv.ChangeOccured += RefreshList;
            ShowBindingList = new BindingList<ShowData>();

            RefreshList();
        }

        private void RefreshList()
        {
            ShowBindingList.Clear();
            foreach (var showData in serv.GetAll())
            {
                ShowBindingList.Add(showData);
            }
        }

        public void AddPurchase(int showId, string clientName, int quantity)
        {
            serv.AddPurchase(showId, clientName, quantity);
        }

        public bool Login(string username, string password)
        {
            return serv.Login(username, password);
        }



    }
}
