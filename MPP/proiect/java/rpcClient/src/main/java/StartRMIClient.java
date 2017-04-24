import Ui.Gui;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServerService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by vitiv on 4/24/17.
 */
public class StartRMIClient extends Application {
    public void start(Stage primaryStage) throws Exception{
   /* if (System.getSecurityManager() == null) {
        System.setSecurityManager(new SecurityManager());
    }*/
        try {

            ApplicationContext factory = new ClassPathXmlApplicationContext("SpringClient.xml");
            IServerService server=(IServerService) factory.getBean("chatService");
            Controller ctrl = new Controller(server);

            Gui gui = new Gui(primaryStage, ctrl);
            gui.run();
        } catch (Exception e) {
            System.err.println("Exception:"+e);
            e.printStackTrace();
        }

    }
}
