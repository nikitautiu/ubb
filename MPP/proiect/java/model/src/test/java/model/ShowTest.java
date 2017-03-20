package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/18/17.
 */
public class ShowTest {
    Show testShow;

    @Before
    public void setUp() throws Exception {
        Date testDate = new GregorianCalendar(2016, 12, 7).getTime();
        this.testShow = new Show(1, 2, 3, testDate, 100);
    }

    @Test
    public void getId() throws Exception {
        assertEquals((int)testShow.getId(), 1);
    }

    @Test
    public void getArtistId() throws Exception {
        assertEquals(testShow.getArtistId(), 2);
    }

    @Test
    public void getStartTime() throws Exception {
        Date compDate = new GregorianCalendar(2016, 12, 7).getTime();
        assertEquals(testShow.getStartTime(), compDate);
    }

    @Test
    public void getAvailableSeats() throws Exception {
        assertEquals(testShow.getAvailableSeats(), 100);
    }

    @Test
    public void equals() throws Exception {
        Date compDate = new GregorianCalendar(2016, 12, 7).getTime();
        Show compShow = new Show(1, 2, 3, compDate, 100);
        assertEquals(testShow, compShow);
    }

}