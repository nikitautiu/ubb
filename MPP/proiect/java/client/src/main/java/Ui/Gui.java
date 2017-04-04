package Ui;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by vitiv on 3/26/17.
 */
public class Gui {
    private Stage primaryStage;
    private Controller ctrl;

    public Gui(Stage primaryStage, Controller ctrl) {
        this.primaryStage = primaryStage;
        this.ctrl = ctrl;
    }

    public void run() throws IOException {
        if(tryLogin()) {
            FxmlController controller = new FxmlController();

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Gui.class.getResource("/Main.fxml"));
            loader.setController(controller);
            SplitPane pane = loader.load();

            controller.setCrudControllers(ctrl);

            primaryStage.setScene(new Scene(pane));
            primaryStage.setTitle("Festival");
            primaryStage.show();
        }
    }

    private boolean tryLogin() {
        boolean ok = false;
        while(!ok) {
            ok = true;
            Optional<Pair<String, String>> loginData = LoginDialog.getLogin();
            if(loginData.isPresent()) {
                if(!ctrl.login(loginData.get().getKey(), loginData.get().getValue())) {
                    ok = false;
                    JOptionPane.showMessageDialog(null, "Invalid user/password",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                    return true;
            }
        }
        return false;
    }
}
