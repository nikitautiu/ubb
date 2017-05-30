package repository;

import model.Location;
import utils.DbConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by vitiv on 3/19/17.
 */
public class LocationSqlRepo implements ICrudRepository<Location, Integer> {
    private DbConnManager connManager;


    public LocationSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location add(Location entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Location> getAll() {
        Connection con = connManager.getConnection();
        ArrayList<Location> Locations = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Location")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    Locations.add(new Location(id, name));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return Locations;
    }
}
