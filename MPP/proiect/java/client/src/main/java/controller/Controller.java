package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Purchase;
import model.ShowData;
import services.IClientService;
import services.IServerService;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by vitiv on 3/21/17.
 */
public class Controller implements IClientService {
    private IServerService serverService;
    private ObservableList<ShowData> dataModel;

    public ObservableList<ShowData> getDataModel() {
        return dataModel;
    }


    public Controller(IServerService serverService) {
        this.serverService = serverService;
        dataModel = FXCollections.observableArrayList();
    }

    public void addPurchase(int showId, String name, int quantity)  {
        serverService.addPurchase(new Purchase(0, showId, name, quantity));
    }

    private void populateList()  {
        dataModel.clear();
        Iterable<ShowData> tasks = serverService.getAll();
        tasks.forEach(x -> dataModel.add(x));
    }


    public boolean login(String username, String password)  {
        if(serverService.login(username, password, this)) {
            populateList();
            return true;
        }
        return false;
    }

    @Override
    public void changesOccurred(Collection<ShowData> changedValues) {
        HashMap<Integer, ShowData> newVals = new HashMap<>();
        changedValues.forEach(x -> newVals.put(x.getId(), x));
        dataModel.replaceAll(x -> newVals.getOrDefault(x.getId(), x));
    }
}
