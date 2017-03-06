import Controller.Controller;
import Repository.*;
import View.Console;
/**
 * Main class
 * Instantiates the class hierarchy and runs the
 * main ui loop.
 */
public class StartApp {
    public static void main(String args[]) {
        MovieRepo movieRepo;
        ClientRepo clientRepo;
        RentalRepo rentalRepo;
        rentalRepo = new RentalRepo();

        try {
            movieRepo = new MovieFileRepo("movies.txt");
            clientRepo = new ClientFileRepo("clients.txt");
            rentalRepo = new RentalRepo();
        } catch(RepositoryException exc) {
            System.out.printf("Corrupted file.");
            return;
        }
        Controller controller = new Controller(movieRepo, clientRepo, rentalRepo);
        Console view = new Console(controller);

        view.runUI(); // runs the ui loop
    }
}
