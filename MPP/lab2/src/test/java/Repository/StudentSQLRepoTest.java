package Repository;

import Domain.Student;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/7/17.
 */
public class StudentSQLRepoTest {

    @Test
    public void testAll() throws Exception {
        Properties props = new Properties();
        props.load(new FileReader("bd.config"));
        StudentSQLRepo repo = new StudentSQLRepo(props);

        repo.add(new Student(1, "a", "aa", 9.5));
        repo.add(new Student(2, "ab", "ab", 9.6));

        assertEquals(repo.size(), 2);

        String nume2 = repo.find(2).getNume();
        assertEquals(nume2, "ab");
    }
}