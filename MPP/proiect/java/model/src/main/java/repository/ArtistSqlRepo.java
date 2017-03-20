package repository;

import model.Artist;
import utils.DbConnManager;

import javax.naming.OperationNotSupportedException;
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
public class ArtistSqlRepo implements ICrudRepository<Artist, Integer> {
    private DbConnManager connManager;


    public ArtistSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Artist entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Artist find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Artist> getAll() {
        Connection con = connManager.getConnection();
        ArrayList<Artist> artists = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Artist")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    artists.add(new Artist(id, name));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return artists;
    }
}
