using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Service;

namespace Festival.Controller
{
    
    public class Controller: IClientService
    {
        private IServerService serv;
        public IBindingList ShowBindingList { get; }

        public Controller(IServerService serv)
        {
            this.serv = serv;
            ShowBindingList = new BindingList<ShowData>();           
        }

        private SynchronizationContext threadingContext;

        public void setThreadingContext(SynchronizationContext threadingContext)
        {
            // used for updating when ui is in another thread
            this.threadingContext = threadingContext;
        }

        public void AddPurchase(int showId, string clientName, int quantity)
        {
            serv.AddPurchase(showId, clientName, quantity);
        }

        public bool Login(string username, string password)
        {
            if (serv.Login(username, password, this))
            {
                ShowBindingList.Clear();
                foreach (var showData in serv.GetAll())
                {
                    ShowBindingList.Add(showData);
                }
                return true;
            }
            return false;
        }

        public void handleUpdate(IEnumerable<ShowData> changed)
        {
            // see on what pos is which id
            var asoc = new Dictionary<int, int>();
            for (var i = 0; i < ShowBindingList.Count; ++i)
                asoc[((ShowData) ShowBindingList[i]).Id] = i;
            // update corersponding items
            foreach (var show in changed)
                ShowBindingList[asoc[show.Id]] = show;
        }

        public void changesOccured(IEnumerable<ShowData> changed)
        {
            if (threadingContext != null)
            {
                threadingContext.Post(state =>
                {
                    this.handleUpdate((IEnumerable<ShowData>) state);
                }, changed);
            }
            else
            {
                this.handleUpdate(changed);
            } 
               
        }
    }
}
