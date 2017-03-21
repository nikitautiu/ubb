import Ui.FxmlController;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Purchase;
import model.dtos.ShowData;
import repository.ICrudRepository;
import repository.PurchaseSqlRepo;
import repository.ShowDataSqlRepo;
import services.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vitiv on 3/21/17.
 */
public class StartApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            props.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return;
        }

        ICrudRepository<Purchase, Integer> purchaseRepo =
                new PurchaseSqlRepo(props);
        ICrudRepository<ShowData, Integer> showDataRepo =
                new ShowDataSqlRepo(props);

        Service service = new Service(purchaseRepo, showDataRepo);
        Controller ctrl = new Controller(service);
        FxmlController controller = new FxmlController();

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(StartApp.class.getResource("/Main.fxml"));
        loader.setController(controller);
        SplitPane pane = loader.load();

        controller.setCrudControllers(ctrl);

        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Festival");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

