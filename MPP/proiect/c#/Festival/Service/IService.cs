using System.Collections.Generic;
using Festival.Model;

namespace Festival.Service
{
    public interface IService
    {
        event changeOccuredType ChangeOccured; //tine locul clientului deoarece are loc un singur event

        void AddPurchase(int showId, string clientName, int quantity);
        bool Login(string username, string password);
        IEnumerable<ShowData> GetAll();
    }
}