package services;

import model.Purchase;
import model.dtos.ShowData;
import repository.ICrudRepository;
import utils.IObservable;
import utils.IObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vitiv on 3/21/17.
 */
public class Service implements IObservable<Integer> {
    private ICrudRepository<Purchase, Integer> purchaseRepo;
    private ICrudRepository<ShowData, Integer> showRepo;
    private List<IObserver<Integer>> observers = new ArrayList<>();


    public Service(ICrudRepository<Purchase, Integer> purchaseRepo, ICrudRepository<ShowData, Integer> showRepo) {
        this.purchaseRepo = purchaseRepo;
        this.showRepo = showRepo;
    }

    public void addPurchase(Purchase entity) {
        purchaseRepo.add(entity);
        notifyObservers(null);
    }

    public Collection<ShowData> getAll(){
        return showRepo.getAll();
    }

    @Override
    public void addObserver(IObserver<Integer> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(IObserver<Integer> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Integer t) {
        observers.stream().forEach(x->x.update(t));
    }
}
