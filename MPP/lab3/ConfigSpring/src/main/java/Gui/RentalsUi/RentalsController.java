package Gui.RentalsUi;

import Controller.Controller;
import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Utils.Helpers;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by archie on 12/8/2016.
 */

public class RentalsController {
    @FXML private TableColumn<Rental, String> moviesColumn;
    @FXML private TableColumn<Rental, String> clientsColumn;
    @FXML private TableColumn<Movie, String> moviesListColumn;
    @FXML private TableColumn<Client, String> clientsListColumn;
    @FXML private Button rentBtn;
    @FXML private Button unrentBtn;
    @FXML private TableView<Rental> rentalsTable;
    @FXML private TableView<Client> clientsListTable;
    @FXML private TableView<Movie> moviesListTable;

    private Controller controller;
    private ObservableList<Client> clientModel;
    private ObservableList<Movie> movieModel;
    private ObservableList<Rental> rentalModel;

    private HashMap<Integer, String> movieNames;
    private HashMap<Integer, String> clientNames;

    public void setCrudControllers(Controller controller) {
        this.controller = controller;
        controller.getClientObservable().addObserver(e -> {updateClientModel(); updateRentalModel();});
        controller.getMovieObservable().addObserver(e -> {updateMovieModel(); updateRentalModel();});
        controller.getRentalObservable().addObserver(e -> updateRentalModel());

        updateClientModel();
        updateMovieModel();
        updateRentalModel();
    }

    @FXML
    public void initialize() {
        // set the columns to readony values
        moviesColumn.setCellValueFactory(p ->
            new ReadOnlyStringWrapper(movieNames.get(p.getValue().getMovieId()))
        );
        clientsColumn.setCellValueFactory(p ->
            new ReadOnlyStringWrapper(clientNames.get(p.getValue().getClientId()))
        );

        moviesListColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientsListColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        toggleBtns();

        rentalsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toggleBtns();
        });
        moviesListTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toggleBtns();
        });
        clientsListTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toggleBtns();
        });
    }

    @FXML
    public void rentBtnHandler() throws IOException {
        int movieId = moviesListTable.getSelectionModel().getSelectedItems().get(0).getId();
        int clientId = clientsListTable.getSelectionModel().getSelectedItems().get(0).getId();
        try {
            controller.addRental(new Rental(0, movieId, clientId));
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
    public void unrentBtnHandler() throws IOException {
        int rentalId = rentalsTable.getSelectionModel().getSelectedItems().get(0).getId();
        try {
            controller.deleteRental(rentalId);
        } catch(Exception ex) {
            String errTitle = "Eroare";
            String errMsg = ex.getMessage();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errTitle);
            alert.setContentText( errMsg);
            alert.showAndWait();
        }

    }

    private void toggleBtns() {
        ObservableList selectedClients = clientsListTable.getSelectionModel().getSelectedItems();
        ObservableList selectedMovies = moviesListTable.getSelectionModel().getSelectedItems();
        ObservableList selectedRentals = rentalsTable.getSelectionModel().getSelectedItems();

        boolean rentDisabled = false;
        boolean unrentDisabled = false;
        if((selectedClients == null || selectedClients.size() == 0) ||
            (selectedMovies == null || selectedMovies.size() == 0)) {
            rentDisabled = true;
        }
        if(selectedRentals == null || selectedRentals.size() == 0)
            unrentDisabled = true;
        rentBtn.setDisable(rentDisabled);
        unrentBtn.setDisable(unrentDisabled);
    }

    public void updateClientModel() {
        clientNames = new HashMap<>();
        for(Client client : controller.getClients())
            clientNames.put(client.getId(), client.getName());

        clientModel = FXCollections.observableArrayList(Helpers.makeCollection(controller.getClients()));
        clientsListTable.setItems(clientModel);
    }

    public void updateMovieModel() {
        movieNames = new HashMap<>();
        for(Movie movie : controller.getMovies())
            movieNames.put(movie.getId(), movie.getName());

        movieModel = FXCollections.observableArrayList(Helpers.makeCollection(controller.getMovies()));
        moviesListTable.setItems(movieModel);
    }

    public void updateRentalModel() {
        rentalModel = FXCollections.observableArrayList(Helpers.makeCollection(controller.getRentals()));
        rentalsTable.setItems(rentalModel);
    }

}
