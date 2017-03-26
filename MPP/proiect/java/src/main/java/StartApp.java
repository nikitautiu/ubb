import Ui.Gui;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Purchase;
import model.dtos.ShowData;
import repository.*;
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
        IUserRepo userRepo = new UserSqlRepo(props);

        Service service = new Service(purchaseRepo, showDataRepo, userRepo);
        Controller ctrl = new Controller(service);
        Gui gui = new Gui(primaryStage, ctrl);
        gui.run();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

