import model.Purchase;
import model.ShowData;
import networking.utils.AbstractServer;
import networking.utils.JsonConcurrentServer;
import networking.utils.ServerException;
import repository.*;
import server.Server;
import services.IServerService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vitiv on 4/3/17.
 */
public class StartServer {
    private static int defaultPort=55555;
    private static String defaultServer="localhost";

    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("server.config"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find properties "+e);
            return;
        }
        ICrudRepository<Purchase, Integer> purchaseRepo =
                new PurchaseSqlRepo(serverProps);
        ICrudRepository<ShowData, Integer> showDataRepo =
                new ShowDataSqlRepo(serverProps);
        IUserRepo userRepo = new UserSqlRepo(serverProps);

        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);

        IServerService serverImpl = new Server(showDataRepo, userRepo, purchaseRepo);
        AbstractServer server = new JsonConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
