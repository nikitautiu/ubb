import repository.ArtistSqlRepo;
import model.Artist;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/20/17.
 */
public class ArtistSqlRepoTest {
    private ArtistSqlRepo repo;

    @Before
    public void setUp() throws Exception {
        Properties props = new Properties();
        props.setProperty("jdbc.driver", "org.sqlite.JDBC");
        props.setProperty("jdbc.url", "jdbc:sqlite:../test.db");

        repo = new ArtistSqlRepo(props);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Artist> artists = repo.getAll();
        ArrayList<Artist> expected = new ArrayList<>();
        expected.add(new Artist(1, "Whitesnake"));
        expected.add(new Artist(2, "Mastodon"));

        assertEquals(artists, expected);
    }

}