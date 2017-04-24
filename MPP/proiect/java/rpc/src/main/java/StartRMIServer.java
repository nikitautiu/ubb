import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vitiv on 4/24/17.
 */
public class StartRMIServer {
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        try {
            ApplicationContext factory = new ClassPathXmlApplicationContext("SpringServer.xml");
        } catch (Exception e) {
            System.err.println("Server exception:"+e);
            e.printStackTrace();
        }
    }

}
