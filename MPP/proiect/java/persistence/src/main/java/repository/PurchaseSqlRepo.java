package repository;

import model.Purchase;
import model.validator.PurchaseValidator;
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

        Connection con = connManager.getConnection();
        Helpers helpers = new Helpers(entity, con).invoke();

        new PurchaseValidator().accept(entity);

        if(!doesIdExist(entity, con))
            throw new RepositoryException("Show id not in db");

        int sold = helpers.getSold();
        int total = helpers.getTotal();
        if(entity.getQuantity() + sold > total)
            throw new RepositoryException("Purchase exceeds total seats");

        // otherwise
        try (PreparedStatement preStmt = con.prepareStatement("insert into Purchase(showId, clientName, quantity) values(?, ?, ?)")) {
            preStmt.setInt(1, entity.getShowId());
            preStmt.setString(2, entity.getClientName());
            preStmt.setInt(3, entity.getQuantity());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    private boolean doesIdExist(Purchase entity, Connection con) {

        try (PreparedStatement preStmt = con.prepareStatement("select exists(select * from Show where id=?)")) {
            preStmt.setInt(1, entity.getShowId());
            try (ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    return result.getBoolean(1);
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex.getSQLState());
        }
        return false;
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
            throw new RepositoryException(ex.getSQLState());
        }

        return Purchases;
    }

    private static class Helpers {
        private Purchase entity;
        private Connection con;
        private int sold;
        private int total;

        public Helpers(Purchase entity, Connection con) {
            this.entity = entity;
            this.con = con;
        }

        public int getSold() {
            return sold;
        }

        public int getTotal() {
            return total;
        }

        public Helpers invoke() {
            sold = 0;
            total = 0;
            try (PreparedStatement preStmt = con.prepareStatement("select total(Purchase.quantity) as sold, Show.availableSeats as ava from Show left join Purchase on Purchase.showId=Show.id WHERE Show.id=?")) {
                preStmt.setInt(1, entity.getShowId());
                try (ResultSet result = preStmt.executeQuery()) {
                    if(result.next()) {
                        sold = result.getInt("sold");
                        total = result.getInt("ava");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}
