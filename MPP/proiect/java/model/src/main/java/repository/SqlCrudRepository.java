package repository;

import utils.DbConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class SqlCrudRepository<E, ID> implements ICrudRepository<E, ID> {
    private DbConnManager connManager;
    private ISqlCrudStrategy strat;
    public SqlCrudRepository(Properties props, ISqlCrudStrategy strat) {
        connManager = new DbConnManager(props);
        this.strat = strat;
    }

    @Override
    public long size() {
        Connection con = connManager.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Students")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public void add(E entity) {
        Connection con = connManager.getConnection();

        // Insert with known id(update)
        // insert with autoincrement
        try (PreparedStatement preStmt = strat.getInsertStmt();
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public void delete(ID id) {
        Connection con = connManager.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Students where id=?")) {
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public E find(ID id) {
        Connection con = connManager.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Students where id=?")) {
            preStmt.setInt(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    E stud = start.getEntity(result);
                    return stud;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return null;
    }

//    private E getEntity(ResultSet result) throws SQLException {
//        int id = result.getInt("id");
//        String nume = result.getString("nume");
//        String nrMatricol = result.getString("nrMatricol");
//        Double medie = result.getDouble("medie");
//        return new Student(id, nume, nrMatricol, medie);
//    }

    @Override
    public Collection<E> getAll() {
        return null;
    }
}
