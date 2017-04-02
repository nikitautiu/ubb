package Ui;


import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Show;
import model.dtos.ShowData;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

/**
 * Created by archie on 12/8/2016.
 */

public class FxmlController {
    @FXML
    private TableView<ShowData> table;
    @FXML
    private TableView<ShowData> srcTable;
    @FXML
    private TableColumn<ShowData, Integer> idColumn;
    @FXML
    private TableColumn<ShowData, String> artistColumn;
    @FXML
    private TableColumn<ShowData, String> locationColumn;
    @FXML
    private TableColumn<ShowData, String> startTimeColumn;
    @FXML
    private TableColumn<ShowData, Integer> remainingColumn;
    @FXML
    private TableColumn<ShowData, Integer> idSrcColumn;
    @FXML
    private TableColumn<ShowData, String> artistSrcColumn;
    @FXML
    private TableColumn<ShowData, String> locationSrcColumn;
    @FXML
    private TableColumn<ShowData, String> startTimeSrcColumn;
    @FXML
    private TableColumn<ShowData, Integer> remainingSrcColumn;


    @FXML
    private TextField nameText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField quantityText;
    @FXML
    private Button buyBtn;


    private FilteredList<ShowData> srcModel;
    private Controller controller;

    public void setCrudControllers(Controller controller) {
        this.controller = controller;

        table.setItems(controller.getDataModel());
        srcModel = new FilteredList<ShowData>(controller.getDataModel());
        srcTable.setItems(srcModel);
        handleFilters();

    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        startTimeColumn.setCellValueFactory(
                show -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    property.setValue(dateFormat.format(show.getValue().getStartTime()));
                    return property;
                }
        );
        remainingColumn.setCellFactory(getTableColumnTableCellCallback());
        remainingColumn.setCellValueFactory(p -> {
            SimpleIntegerProperty prop = new SimpleIntegerProperty();
            prop.setValue(p.getValue().getAvailableSeats() - p.getValue().getSoldSeats());
            return prop.asObject();
        });
        idSrcColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        artistSrcColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        locationSrcColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        startTimeSrcColumn.setCellValueFactory(
                show -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    property.setValue(dateFormat.format(show.getValue().getStartTime()));
                    return property;
                }
        );
        remainingSrcColumn.setCellFactory(getTableColumnTableCellCallback());
        remainingSrcColumn.setCellValueFactory(p -> {
            SimpleIntegerProperty prop = new SimpleIntegerProperty();
            prop.setValue(p.getValue().getAvailableSeats() - p.getValue().getSoldSeats());
            return prop.asObject();
        });
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            handleFilters();
        });


    }

    private Callback<TableColumn<ShowData, Integer>, TableCell<ShowData, Integer>> getTableColumnTableCellCallback() {
        return column -> new TableCell<ShowData, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    if(item == 0)
                        setStyle("-fx-background-color: #F00");
                    else
                        setStyle("");
                }
            }
        };
    }

    @FXML
    void handleFilters() {
        Predicate<ShowData> pred;
        if(datePicker.getValue() == null)
            pred = (mov) -> false;
        else
            pred = (show) -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(show.getStartTime());

                int year = cal.get(Calendar.YEAR);
                int year1 = datePicker.getValue().getYear();
                int month = cal.get(Calendar.MONTH) + 1;
                int month2 = datePicker.getValue().getMonthValue();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int day2 = datePicker.getValue().getDayOfMonth();
                return year == year1 &&
                        month == month2 &&
                        day == day2;

            };
        srcModel.setPredicate(pred);
    }

    @FXML
    void handleBuyBtn() {
        int quantity;
        ShowData data;

        try {
            quantity = Integer.parseInt(quantityText.getText());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid quantity",
                    "Invalid quantity",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        data = srcTable.getSelectionModel().getSelectedItem();
        if(data == null) {
            JOptionPane.showMessageDialog(null, "Select a show",
                    "Select a show",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String clientName = nameText.getText();
            controller.addPurchase(data.getId(), clientName, quantity);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
