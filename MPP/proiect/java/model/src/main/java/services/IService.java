package services;

import model.Purchase;
import model.dtos.ShowData;
import utils.IObservable;

import java.util.Collection;

/**
 * Created by archie on 3/28/2017.
 */
public interface IService extends IObservable<Integer> {
    void addPurchase(Purchase entity);

    Collection<ShowData> getAll();

    boolean login(String username, String password);
}
