package services;

import model.Purchase;
import model.ShowData;

import java.util.Collection;

/**
 * Created by archie on 3/28/2017.
 */
public interface IServerService {
    void addPurchase(Purchase entity) ;
    Collection<ShowData> getAll() ;
    boolean login(String username, String password, IClientService client) ;
    void logout(IClientService clientService);
}
