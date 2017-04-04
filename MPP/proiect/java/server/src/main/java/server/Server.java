package server;

import model.Purchase;
import model.ShowData;
import model.User;
import repository.ICrudRepository;
import repository.IUserRepo;
import services.IClientService;
import services.IServerService;
import services.ServiceException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Server implements IServerService {
    private IUserRepo userRepo;
    private ICrudRepository<ShowData, Integer> showRepo;
    private ICrudRepository<Purchase, Integer> purchaseRepo;
    private Collection<IClientService> loggedClients;

    public Server(ICrudRepository<ShowData, Integer> mRepo, IUserRepo uRepo, ICrudRepository<Purchase, Integer> purchaseRepo) {
        this.userRepo = uRepo;
        this.showRepo = mRepo;
        this.purchaseRepo = purchaseRepo;
        loggedClients=new ArrayList<>();
    }


    private final int defaultThreadsNo=5;
    private void notifyClients(Collection<ShowData> values) {
        System.out.println("Changes occurred - notifying clients");

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(IClientService client : loggedClients){
            if (client != null)
                executor.execute(() -> {
                    try {
                        client.changesOccurred(values);
                    } catch (ServiceException e) {
                        System.err.println("Error notifying " + e);
                    }
                });
        }

        executor.shutdown();
    }


    @Override
    public void addPurchase(Purchase entity) {
        purchaseRepo.add(entity);
        this.notifyClients(showRepo.getAll().stream().filter(x->x.getId() == entity.getShowId()).collect(Collectors.toList()));
    }

    @Override
    public Collection<ShowData> getAll() {
        return showRepo.getAll();
    }

    @Override
    public boolean login(String username, String password, IClientService client) {
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
        if(Objects.equals(passHash, user.getPassHash())) {
            loggedClients.add(client);
            return true;
        }
        return false;
    }

    @Override
    public void logout(IClientService clientService) {
        System.out.println("Client logged out");
        this.loggedClients.remove(clientService);
    }
}
