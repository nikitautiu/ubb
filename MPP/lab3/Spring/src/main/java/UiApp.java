import Controller.Controller;
import Gui.GuiHelpers;
import Repository.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by archie on 12/8/2016.
 */
public class UiApp extends Application {
    private Logger logger = LogManager.getLogger(UiApp.class.getName());

    private String[] messages = new String[] {
        "Hello!",
        "Goodbye!",
        "Hi!"
    };

    @Override
    public void start(Stage primaryStage) throws Exception{

        ApplicationContext context=new AnnotationConfigApplicationContext(UiAppConfig.class);
        Controller controller = context.getBean(Controller.class);

        Scene scene = GuiHelpers.buildScene(controller);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movie Rental");
        primaryStage.show();

        logger.traceEntry("Messages {}", (Object)messages);
        logger.traceExit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

