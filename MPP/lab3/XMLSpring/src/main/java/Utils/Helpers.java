package Utils;

import Domain.Client;
import Domain.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by archie on 12/8/2016.
 */
public class Helpers {
    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        ArrayList<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    /**
     * Factory function for generating a predicate fir sorting after the name
     * @param name The name to match against
     */
    public static Predicate<Movie> generateNamePredicate(String name) {
        return (movie) -> movie.getName().contains(name);
    }

    public static Predicate<Movie> generateDirectorPredicate(String dir) {
        return (movie) -> movie.getDirector().contains(dir);
    }

    public static Predicate<Client> generateClientIdPredicate(int id) {
        return (client) -> client.getId() == id;
    }

    public static Predicate<Client> generateClientNamePredicate(String str) {
        return (client) -> client.getName().contains(str);
    }

    public static Comparator<Movie> generateMovieIdComparator() {
        return (m1, m2) -> m2.getId() - m1.getId();  // descending movie comparator
    }

    /**
     * Filters a list by a predicate and returns the resulting list
     * @param list The list to filter
     * @param pred The predicate to filter after
     * @return The filtered list
     */
    public static <E> List<E> filter(List<E> list, Predicate<E> pred, Comparator<E> comp) {
        return list.stream().filter(pred).sorted(comp).collect(Collectors.toList());
    }
}
