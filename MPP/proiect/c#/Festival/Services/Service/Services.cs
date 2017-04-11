using System.Collections.Generic;
using Festival.Model;

namespace Festival.Service
{
    public delegate void changeOccuredType(IEnumerable<ShowData> changed);
    public interface IServerService
    {
        void AddPurchase(int showId, string clientName, int quantity);
        bool Login(string username, string password, IClientService cli);
        IEnumerable<ShowData> GetAll();
    }

    public interface IClientService
    {
        void changesOccured(IEnumerable<ShowData> changed); //tine locul clientului deoarece are loc un singur event
    }

}