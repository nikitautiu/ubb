package repository;

import model.Artist;
import model.validator.ArtistValidator;
import utils.DbConnManager;

import java.sql.*;
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
    public Artist add(Artist entity) {
        Connection con = connManager.getConnection();
        new ArtistValidator().accept(entity);

        if(entity.getId() == null) {
            // insert
            try (PreparedStatement preStmt = con.prepareStatement("insert into Artist(name) values(?)", Statement.RETURN_GENERATED_KEYS)) {
                preStmt.setString(1, entity.getName());
                preStmt.executeUpdate();
                ResultSet result = preStmt.getGeneratedKeys();
                if (result.next()) {
                    entity.setId(result.getInt(1)); // set the added key
                }

            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        } else {
            // update
            try (PreparedStatement preStmt = con.prepareStatement("update Artist set name=? where id=?")) {
                preStmt.setString(1, entity.getName());
                preStmt.setInt(2, entity.getId());
                preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
        return entity;
    }

    @Override
    public void delete(Integer id) {
        Connection con = connManager.getConnection();

        // insert
        try (PreparedStatement preStmt = con.prepareStatement("delete from Artist where id=?")) {
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Artist find(Integer integer) {
        Connection con = connManager.getConnection();
        ArrayList<Artist> artists = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Artist where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    return new Artist(id, name);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return null;
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
