package repository;

import model.User;
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
public class UserSqlRepo implements IUserRepo {
    private DbConnManager connManager;


    public UserSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<User> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findByName(String username) {
        Connection con = connManager.getConnection();
        ArrayList<User> Users = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from User where name=?")) {
            preStmt.setString(1, username);
            try (ResultSet result = preStmt.executeQuery()) {
                if(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String passHash = result.getString("passHash");
                    return new User(id, name, passHash);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return null;
    }
}
