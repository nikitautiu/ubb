package model;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/18/17.
 */
public class ArtistTest {
    Artist testArtist;

    @org.junit.Before
    public void setUp() throws Exception {
        testArtist = new Artist(1, "Whitesnake");
    }

    @org.junit.Test
    public void getId() throws Exception {
        assertEquals((int)testArtist.getId(), 1);
    }


    @org.junit.Test
    public void getName() throws Exception {
        assertEquals(testArtist.getName(), "Whitesnake");
    }


    @org.junit.Test
    public void equals() throws Exception {
        Artist newArtist = new Artist(1, "Whitesnake");
        assertEquals(this.testArtist, newArtist);
    }

}