package Gui.MoviesUi;

import Controller.Controller;
import Domain.Movie;
import Domain.Rental;
import Utils.Helpers;
import Utils.Observable;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by archie on 12/8/2016.
 */

public  class MovieController implements Observer<Movie> {
    @FXML private TableView table;
    @FXML private TableColumn<Movie, Integer> idColumn;
    @FXML private TableColumn<Movie, String> titleColumn;
    @FXML private TableColumn<Movie, Float> genreColumn;
    @FXML private TableColumn<Movie, Float> directorColumn;
    @FXML private TextField idText;
    @FXML private TextField titleText;
    @FXML private TextField genreText;
    @FXML private TextField directorText;
    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Label idLbl;
    @FXML private CheckBox titleFilterCheck;
    @FXML private CheckBox directorFilterCheck;
    @FXML private CheckBox genreFilterCheck;
    @FXML private TextField filterText;
    @FXML private PieChart pieChart;
    @FXML private TextField statsText;

    private Controller controller;
    private FilteredList<Movie> model;

    private int nrStats;

    public void setCrudControllers(Controller controller) {
        this.controller = controller;
        controller.getMovieObservable().addObserver(this);
        controller.getRentalObservable().addObserver(e -> {updatePieChart();});
        controller.getClientObservable().addObserver(e -> {updatePieChart();});

        model = new FilteredList<>(FXCollections.observableArrayList(Helpers.makeCollection(controller.getMovies())));
        table.setItems(model);
        updatePieChart();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        nrStats = 3;

        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);

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
            String title = ((Movie) selectedItems.get(0)).getName();
            String director = ((Movie) selectedItems.get(0)).getDirector();
            String genre = ((Movie) selectedItems.get(0)).getGenre();
            Integer id = ((Movie) selectedItems.get(0)).getId();

            idLbl.setText(id.toString());
            titleText.setText(title);
            directorText.setText(director);
            genreText.setText(genre);
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
        String title = titleText.getText();
        String director = directorText.getText();
        String genre = genreText.getText();
        try {
            controller.addMovie(new Movie(0, title, director, genre));
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
    public void changeStats() {
        String text = statsText.getText();
        try {
            nrStats = Integer.parseInt(text);
            updatePieChart();
        } catch(Exception ex) {
        }
    }

    @FXML
    public void updateBtnHandler() throws IOException {
        int id = ((Movie)table.getSelectionModel().getSelectedItems().get(0)).getId();
        String title = titleText.getText();
        String director = directorText.getText();
        String genre = genreText.getText();
        try {
            controller.updateMovie(new Movie(id, title, genre, director));
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
        int id = ((Movie) table.getSelectionModel().getSelectedItems().get(0)).getId();
        try {
            controller.deleteMovie(id);
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
    public void handleFilter() {
        Predicate<Movie> pred;
        if(filterText.getText().equals("") ||
            (!directorFilterCheck.isSelected() && !genreFilterCheck.isSelected() && !titleFilterCheck.isSelected()))
            pred = (mov) -> true;
        else
            pred = (mov) -> {
                String seq = filterText.getText();
                if(titleFilterCheck.isSelected())
                    if(mov.getName().contains(seq))
                        return true;
                if(directorFilterCheck.isSelected())
                    if(mov.getDirector().contains(seq))
                        return true;
                if(genreFilterCheck.isSelected())
                    if(mov.getGenre().contains(seq))
                        return true;
                return false;
            };
        model.setPredicate(pred);
    }


    @Override
    public void update(Observable<Movie> e) {
        model = new FilteredList<>(FXCollections.observableArrayList(Helpers.makeCollection(controller.getMovies())), model.getPredicate());
        table.setItems(model);
        updatePieChart();
    }


    private void updatePieChart() {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList();
        Collection<Rental> rentals = Helpers.makeCollection(controller.getRentals());
        for(javafx.util.Pair<String, Integer> p : controller.mostRented(nrStats)) {
                pieChartData.add(new PieChart.Data(p.getKey(), p.getValue()));
        }

        this.pieChart.setData(pieChartData);
    }

}
