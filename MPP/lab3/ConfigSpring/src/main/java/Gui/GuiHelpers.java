package Gui;

import Controller.Controller;

import Gui.ClientsUi.ClientsController;
import Gui.MainUi.MainController;
import Gui.MoviesUi.MovieController;
import Gui.RentalsUi.RentalsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by archie on 12/15/2016.
 */
public class GuiHelpers {
    private static Node getNode(String filename, Object fxController) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(GuiHelpers.class.getResource(filename));
        loader.setController(fxController); // set the controller programmatically
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static Scene buildScene(Controller ctrl) {
        // build tab elements
        MovieController movieCtrl =  new MovieController();
        ClientsController clientsCtrl =  new ClientsController();
        RentalsController rentalsCtrl =  new RentalsController();

        Node movieNode = getNode("/Movies.fxml", movieCtrl);
        Node clientsNode = getNode("/Clients.fxml", clientsCtrl);
        Node rentalsNode = getNode("/Rentals.fxml", rentalsCtrl);

        movieCtrl.setCrudControllers(ctrl);
        clientsCtrl.setCrudControllers(ctrl);
        rentalsCtrl.setCrudControllers(ctrl);

        // build main element
        MainController mainCtrl =  new MainController();
        Node mainNode = getNode("/Main.fxml", mainCtrl);
        // add tabs
        mainCtrl.addTab("Movies", movieNode, movieCtrl);
        mainCtrl.addTab("Clients", clientsNode, clientsCtrl);
        mainCtrl.addTab("Rentals", rentalsNode, rentalsCtrl);


        Scene scene = new Scene((Parent) mainNode);
        return scene;
    }
}
