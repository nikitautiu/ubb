package services;

import model.ShowData;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by vitiv on 4/2/17.
 */
public interface IClientService extends Remote {
    void changesOccurred(Collection<ShowData> changedValues) throws RemoteException;

}
