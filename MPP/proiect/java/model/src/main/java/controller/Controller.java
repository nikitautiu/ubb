package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Purchase;
import model.dtos.ShowData;
import services.Service;
import utils.IObserver;

/**
 * Created by vitiv on 3/21/17.
 */
public class Controller implements IObserver<Integer> {
    private Service service;
    private ObservableList<ShowData> dataModel;

    public Controller(Service service) {
        this.service = service;
        service.addObserver(this);
        dataModel = FXCollections.observableArrayList();
        populateList();
    }

    public void addPurchase(int showId, String name, int quantity) {
        service.addPurchase(new Purchase(0, showId, name, quantity));
    }

    private void populateList() {
        dataModel.clear();
        Iterable<ShowData> tasks = service.getAll();
        tasks.forEach(x -> dataModel.add(x));
    }

    @Override
    public void update(Integer integer) {
        populateList();
    }
}
