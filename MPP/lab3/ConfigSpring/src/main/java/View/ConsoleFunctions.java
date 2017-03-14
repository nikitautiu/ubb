package View;

import Domain.Client;
import Domain.Movie;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Console helper functions
 * Provides an envelope for console helper functions
 */
public class ConsoleFunctions {
    public static void printClients(Iterable<Client> clients) {
        Iterator<Client> it = clients.iterator();
        while (it.hasNext()) {
            printClient(it.next());
        }
        System.out.print('\n');
    }

    public static void printMovies(Iterable<Movie> movies) {
        Iterator<Movie> it = movies.iterator();
        while (it.hasNext()) {
            printMovie(it.next());
        }
        System.out.print('\n');
    }

    public static void printMovie(Movie movie) {
        System.out.println("ID: " + movie.getId() + ", Nume: " + movie.getName() + ", gen: " +
                           movie.getGenre() + ", regizor: " + movie.getDirector());
    }

    public static void printClient(Client client) {
        System.out.println("ID: " + client.getId() + ", Nume: " + client.getName());
    }

    public static Movie readMovie() {
        int id;
        String name;
        String genre;
        String director;

        System.out.println("Introdu id-ul: ");
        id = readInt();
        System.out.println("Introdu numele: ");
        name = readLineString();
        System.out.println("Introdu genul: ");
        genre = readLineString();
        System.out.println("Introdu regizorul: ");
        director = readLineString();

        return new Movie(id, name, genre, director);
    }

    public static Client readClient() {
        int id;
        String name;

        System.out.println("Introdu id-ul: ");
        id = readInt();
        System.out.println("Intrdu numele: ");
        name = readLineString();

        return new Client(id, name);
    }

    public static int readInt() {
        Scanner in = new Scanner(System.in);
        boolean ok = false;
        int nr = 0;
        while(!ok) {
            ok = true;
            try {
                nr = in.nextInt();
            } catch (InputMismatchException exc) {
                ok = false;  // not a valid int
                System.out.println("Input invalid. Incearca din nou.");
            }
            in.nextLine();
        }
        return nr;
    }

    public static String readLineString() {
        Scanner in = new Scanner(System.in);
        String readStr = in.nextLine();
        return readStr;
    }
}
