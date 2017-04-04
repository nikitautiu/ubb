import Ui.Gui;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import networking.protocol.ServerObjectProxy;
import services.IServerService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vitiv on 3/21/17.
 */
public class StartApp extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Properties clientProps=new Properties();
        try {
            clientProps.load(new FileReader("client.config"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("server.host", defaultServer);
        int serverPort= defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        IServerService service = new ServerObjectProxy(serverIP, serverPort);
        Controller ctrl = new Controller(service);
        Gui gui = new Gui(primaryStage, ctrl);
        gui.run();



    }

    public static void main(String[] args) {
        launch(args);
    }

}

