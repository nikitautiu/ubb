package Gui.ClientsUi;

import Controller.Controller;
import Domain.Client;
import Utils.Helpers;
import Utils.Observable;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Created by archie on 12/8/2016.
 */

public  class ClientsController implements Observer<Client> {
    @FXML private TableView table;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nameColumn;
    @FXML private TextField nameText;
    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Label idLbl;
    @FXML private TextField filterText;

    private Controller controller;
    private FilteredList<Client> model;

    public void setCrudControllers(Controller controller) {
        this.controller = controller;
        controller.getClientObservable().addObserver(this);

        model = new FilteredList<>(FXCollections.observableArrayList(Helpers.makeCollection(controller.getClients())));
        table.setItems(model);
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        toggleBtns();
        populateFields();

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toggleBtns();
            if(oldSelection != newSelection) {
                populateFields();
            }
        });
    }

    private void populateFields() {
        ObservableList selectedItems = table.getSelectionModel().getSelectedItems();
        if (selectedItems.size() == 1) {
            // single selection => populate fields
            String name = ((Client) selectedItems.get(0)).getName();
            Integer id = ((Client) selectedItems.get(0)).getId();

            idLbl.setText(id.toString());
            nameText.setText(name);
        }
        if (selectedItems.size() == 0) {
            // clear selection
            idLbl.setText("");
        }
    }

    private void toggleBtns() {
        ObservableList selectedItems = table.getSelectionModel().getSelectedItems();
        boolean deleteDisabled = false;
        boolean updateDisabled = false;
        if(selectedItems == null || selectedItems.size() == 0) {
            deleteDisabled = true;
            updateDisabled = true;
        }
        else if(selectedItems.size() != 1)
            updateDisabled = true;
        updateBtn.setDisable(updateDisabled);
        deleteBtn.setDisable(deleteDisabled);
    }

    @FXML
    public void addBtnHandler() throws IOException {
        String name = nameText.getText();
        try {
            controller.addClient(new Client(0, name));
        } catch(Exception ex) {
            String errTitle = "Eroare";
            String errMsg = ex.getMessage();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errTitle);
            alert.setContentText( errMsg);
            alert.showAndWait();
        }
    }

    @FXML
    public void updateBtnHandler() throws IOException {
        int id = ((Client)table.getSelectionModel().getSelectedItems().get(0)).getId();
        String name = nameText.getText();
        try {
            controller.updateClient(new Client(id, name));
        } catch(Exception ex) {
            String errTitle = "Eroare";
            String errMsg = ex.getMessage();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errTitle);
            alert.setContentText( errMsg);
            alert.showAndWait();
        }

    }

    @FXML
    public void deleteBtnHandler() throws IOException {
        int id = ((Client) table.getSelectionModel().getSelectedItems().get(0)).getId();
        try {
            controller.deleteClient(id);
        } catch(Exception ex) {
            String errTitle = "Eroare";
            String errMsg = ex.getMessage();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errTitle);
            alert.setContentText( errMsg);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleFilters() {
        Predicate<Client> pred;
        if(filterText.getText().equals(""))
            pred = (mov) -> true;
        else
            pred = (cli) -> {
                String seq = filterText.getText();
                if(cli.getName().contains(seq))
                    return true;

                return false;
            };
        model.setPredicate(pred);
    }

    @FXML
    public void clearBtnHandler() {
        Predicate<Client> pred = (cli) -> true;
        model.setPredicate(pred);
    }

    @Override
    public void update(Observable<Client> e) {
        model = new FilteredList<>(FXCollections.observableArrayList(Helpers.makeCollection(controller.getClients())), model.getPredicate());
        table.setItems(model);
    }

}
