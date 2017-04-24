import model.Purchase;
import model.ShowData;
import repository.*;
import server.Server;
import services.IServerService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vitiv on 4/24/17.
 */
public class ServerFactory {


    public static IServerService invoke() {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("server.config"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find properties " + e);
        }
        ICrudRepository<Purchase, Integer> purchaseRepo =
                new PurchaseSqlRepo(serverProps);
        ICrudRepository<ShowData, Integer> showDataRepo =
                new ShowDataSqlRepo(serverProps);
        IUserRepo userRepo = new UserSqlRepo(serverProps);

       return new Server(showDataRepo, userRepo, purchaseRepo);
    }
}
