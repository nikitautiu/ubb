package Repository;

import Domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by vitiv on 3/7/17.
 */
public class StudentSQLRepo implements ICrudRepository<Student, Integer> {
    private DbConnManager connManager;

    public StudentSQLRepo(Properties props) {
        connManager = new DbConnManager(props);
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
    public void add(Student entity) {
        Connection con = connManager.getConnection();

        // Insert with known id(update)
         // insert with autoincrement
        try (PreparedStatement preStmt = con.prepareStatement("insert into Students(id ,nume, nrMatricol, medie) values (?, ?,?,?)")) {
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getNume());
            preStmt.setString(3, entity.getNrMatricol());
            preStmt.setDouble(4, entity.getMedie());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con = connManager.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Students where id=?")) {
            preStmt.setInt(1, integer);
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Student find(Integer integer) {
        Connection con = connManager.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Students where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String nrMatricol = result.getString("nrMatricol");
                    Double medie = result.getDouble("medie");
                    Student stud = new Student(id, nume, nrMatricol, medie);
                    return stud;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return null;
    }

    @Override
    public Iterable<Student> getAll() {
        return null;
    }
}
