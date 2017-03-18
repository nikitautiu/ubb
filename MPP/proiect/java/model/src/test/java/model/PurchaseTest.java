package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vitiv on 3/18/17.
 */
public class PurchaseTest {
    Purchase testPurchase;

    @Before
    public void setUp() throws Exception {
        this.testPurchase = new Purchase(1, 2, "Gelu", 3);
    }

    @Test
    public void getId() throws Exception {
        assertEquals((int)testPurchase.getId(), 1);
    }

    @Test
    public void getShowId() throws Exception {
        assertEquals((int)testPurchase.getShowId(), 2);
    }

    @Test
    public void getClientName() throws Exception {
        assertEquals(testPurchase.getClientName(), "Gelu");
    }

    @Test
    public void getQuantity() throws Exception {
        assertEquals(testPurchase.getQuantity(), 3);
    }

    @Test
    public void equals() throws Exception {
        Purchase newPurchase = new Purchase(1, 2, "Gelu", 3);
        assertEquals(testPurchase, newPurchase);
    }

}