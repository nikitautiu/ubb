package repository;

import model.Show;
import utils.DbConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by vitiv on 3/19/17.
 */
public class ShowSqlRepo implements ICrudRepository<Show, Integer> {
    private DbConnManager connManager;


    public ShowSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Show add(Show entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Show find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Show> getAll() {
        Connection con = connManager.getConnection();
        ArrayList<Show> shows = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select (id, artistId, locationId, datetime([Show.startTime]) as startTime, availableSeats) from Show")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int id = result.getInt("id");
                    int artistId = result.getInt("artistId");
                    int locationId = result.getInt("locationId");
                    Date startTime  = result.getDate("startTime");
                    int availableSeats = result.getInt("availableSeats");

                    shows.add(new Show(id, artistId, locationId, startTime, availableSeats ));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

        return shows;
    }
}
