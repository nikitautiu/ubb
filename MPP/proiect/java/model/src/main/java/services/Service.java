package services;

import model.Purchase;
import model.User;
import model.dtos.ShowData;
import repository.ICrudRepository;
import repository.IUserRepo;
import utils.IObserver;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by vitiv on 3/21/17.
 */
public class Service implements IService {
    private ICrudRepository<Purchase, Integer> purchaseRepo;
    private ICrudRepository<ShowData, Integer> showRepo;
    private IUserRepo userRepo;
    private List<IObserver<Integer>> observers = new ArrayList<>();


    public Service(ICrudRepository<Purchase, Integer> purchaseRepo, ICrudRepository<ShowData, Integer> showRepo, IUserRepo userRepo) {
        this.purchaseRepo = purchaseRepo;
        this.showRepo = showRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void addPurchase(Purchase entity) {
        purchaseRepo.add(entity);
        notifyObservers(null);
    }

    @Override
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

    @Override
    public boolean login(String username, String password) {
        User user = userRepo.findByName(username);
        if(user == null)
            return false;

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(password.getBytes(),0,password.length());
        String passHash = new BigInteger(1 ,m.digest()).toString(16);
        return Objects.equals(passHash, user.getPassHash());
    }
}
