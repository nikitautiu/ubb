package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Purchase;
import model.ShowData;
import services.IClientService;
import services.IServerService;

import java.util.Collection;

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
        populateList();
    }

    public void addPurchase(int showId, String name, int quantity) {
        serverService.addPurchase(new Purchase(0, showId, name, quantity));
        populateList();
    }

    private void populateList() {
        dataModel.clear();
        Iterable<ShowData> tasks = serverService.getAll();
        tasks.forEach(x -> dataModel.add(x));
    }


    public boolean login(String username, String password) {
        return serverService.login(username, password, this);
    }

    @Override
    public void changesOccurred(Collection<ShowData> changedValues) {
        populateList();
    }
}
