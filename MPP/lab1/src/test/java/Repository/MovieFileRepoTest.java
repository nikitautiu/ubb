package Repository;

import Domain.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nichitautiu on 10/11/2016.
 */
public class MovieFileRepoTest {
    @Test
    public void testLoadFromFile() {
        MovieFileRepo testRepo = new MovieFileRepo("testmovies.txt");
        // delete all
        for(Movie movie : testRepo.getAll())
            testRepo.delete(movie.getId());

        List<Movie> expectedMovies = new ArrayList<>();
        expectedMovies.add(new Movie(1, "Shawshank Redemption", "Drama", "Frank Darabont"));
        expectedMovies.add(new Movie(2, "Django Unchained", "Action", "Quentin Tarantino"));
        expectedMovies.add(new Movie(3, "2001: A Space Oddesy", "Sci-fi", "Stanley Kubrick"));

        for(Movie movie : expectedMovies)
            testRepo.add(movie);  // write to file

        testRepo = new MovieFileRepo("testmovies.txt"); // reinitialize / load from file
        List<Movie> receivedMovies = new ArrayList<>();
        for(Movie movie : testRepo.getAll())
            receivedMovies.add(movie);

        assertTrue("Loading/saving to file failed", receivedMovies.equals(expectedMovies));
    }
}