package Repository;

import Domain.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nichitautiu on 10/11/2016.
 */
public class MovieRepoTest {
    @Test
    public void testMovieRepo() {
        MovieRepo repo = new MovieRepo();
        List<Movie> receivedList = new ArrayList<Movie>();
        for(Movie movie : repo.getAll())
            receivedList.add(movie);

        assertTrue("getting failed", receivedList.equals(new ArrayList<Movie>()));

        List<Movie> expectedList = Arrays.asList(
                new Movie(1, "aa", "bb", "aa"),
                new Movie(2, "bb", "aa", "bb"));
        // add the m to the repo
        repo.add(new Movie(1, "aa", "bb", "aa"));
        repo.add(new Movie(2, "bb", "aa", "bb"));

        receivedList = new ArrayList<>();
        for(Movie movie : repo.getAll())
            receivedList.add(movie);
        assertTrue("adding failed", receivedList.equals(expectedList));

        assertEquals("size failed", 2, repo.size());
        assertNull("find failed", repo.find(3));
        assertEquals("find failed", repo.find(1), new Movie(1, "aa", "bb", "aa"));
    }
}