package repository;

import model.Purchase;
import utils.DbConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by vitiv on 3/19/17.
 */
public class PurchaseSqlRepo implements ICrudRepository<Purchase, Integer> {
    private DbConnManager connManager;


    public PurchaseSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Purchase entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Purchase find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Purchase> getAll() {
        Connection con = connManager.getConnection();
        ArrayList<Purchase> Purchases = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Purchase")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int id = result.getInt("id");
                    int showId = result.getInt("showId");
                    String clientName = result.getString("clientName");
                    int quantity = result.getInt("quantity");

                    Purchases.add(new Purchase(id, showId, clientName, quantity));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

        return Purchases;
    }
}
