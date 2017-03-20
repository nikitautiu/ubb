package repository;

import model.Purchase;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/20/17.
 */
public class PurchaseSqlRepoTest {
    private PurchaseSqlRepo repo;

    @Before
    public void setUp() throws Exception {
        Properties props = new Properties();
        props.setProperty("jdbc.driver", "org.sqlite.JDBC");
        props.setProperty("jdbc.url", "jdbc:sqlite:../test.db");

        repo = new PurchaseSqlRepo(props);
    }

    @Test
    public void add() throws Exception {
        boolean threw = false;
        try {
            repo.add(new Purchase(0, 5, "gelu", 100)); // bad id
        } catch(Exception ex) {
            threw = true;
        }
        assertTrue(threw);
        threw = false;
        try {
            repo.add(new Purchase(0, 1, "gelu", 10000)); // bad quantity
        } catch(Exception ex) {
            threw = true;
        }
        assertTrue(threw);
    }


}