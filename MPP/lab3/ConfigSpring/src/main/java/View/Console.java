package View;

import Controller.Controller;
import Domain.Client;
import Domain.Validator.ValidationException;
import Utils.Helpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Console class
 * Receives a controller on which it performs operations
 */
public class Console {
    private Controller controller;

    /**
     * Console UI constructor
     *
     * @param controller
     */
    public Console(Controller controller) {
        this.controller = controller;
    }

    /**
     * UI loop method
     * Runs until the user exists the program.
     */
    public void runUI() {
        int option;

        while (true) {
            System.out.println("1 - afiseaza clienti\n" +
                    "2 - afiseaza filme\n" +
                    "3 - adauga client\n" +
                    "4 - adauga film\n" +
                    "5 - sterge client\n" +
                    "6 - sterge film\n" +
                    "7 - filrtreaza filme dupa nume\n" +
                    "8 - filtreaza filme dupa regizor\n" +
                    "9 - filtreaza clienti dupa id\n" +
                    "10 - filtreaza clienti dupa nume\n" +
                    "0 - iesi din aplicatie");
            option = ConsoleFunctions.readInt();

            try {
                if (option == 0)
                    break;
                else if (option == 1)
                    ConsoleFunctions.printClients(controller.getClients());
                else if (option == 2)
                    ConsoleFunctions.printMovies(controller.getMovies());
                else if (option == 3) {
                    controller.addClient(ConsoleFunctions.readClient());
                } else if (option == 4) {
                    controller.addMovie(ConsoleFunctions.readMovie());
                } else if (option == 5) {
                    System.out.println("Introdu id-ul clientului pe care vrei sa-l stergi: ");
                    controller.deleteClient(ConsoleFunctions.readInt());
                } else if (option == 6) {
                    System.out.println("Introdu id-ul filmului pe care vrei sa-l stergi");
                    controller.deleteMovie(ConsoleFunctions.readInt());
                } else if(option == 7) {
                    System.out.println("Introdu titlul care sa fie cautat");
                    String str = ConsoleFunctions.readLineString();
                    ConsoleFunctions.printMovies(Helpers.filter(
                            iterToList(controller.getMovies()),
                            Helpers.generateNamePredicate(str),
                            Helpers.generateMovieIdComparator()));
                }
                else if(option == 8) {
                    System.out.println("Introdu numele regizorului dupa care sa se faca cautare");
                    String str = ConsoleFunctions.readLineString();
                    ConsoleFunctions.printMovies(Helpers.filter(
                            iterToList(controller.getMovies()),
                            Helpers.generateDirectorPredicate(str),
                            Helpers.generateMovieIdComparator()));
                } else if(option == 9) {
                    System.out.println("Introdu id-ul cautat");
                    Integer id = ConsoleFunctions.readInt();
                    ConsoleFunctions.printClients(Helpers.filter(
                            iterToList(controller.getClients()),
                            Helpers.generateClientIdPredicate(id),
                            Comparator.comparingInt(Client::getId)));
                } else if(option == 10) {
                    System.out.println("Introdu numele dupa care sa se faca cautarea");
                    String str = ConsoleFunctions.readLineString();
                    ConsoleFunctions.printClients(Helpers.filter(
                            iterToList(controller.getClients()),
                            Helpers.generateClientNamePredicate(str),
                            Comparator.comparingInt(Client::getId)));
                }
                else
                    System.out.println("optiune invalida");
            } catch(ValidationException exc) {
                System.out.println("ERROR: " + exc.getMessage());  // prints all the error messages
            }
        }
    }

    private <E> List<E> iterToList(Iterable<E> movies) {
        return StreamSupport.stream(movies.spliterator(), false)
                     .collect(Collectors.toList());
    }
}
