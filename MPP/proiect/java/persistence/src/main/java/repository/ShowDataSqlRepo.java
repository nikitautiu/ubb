package repository;

import model.ShowData;
import utils.DbConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * Created by vitiv on 3/19/17.
 */
public class ShowDataSqlRepo implements ICrudRepository<ShowData, Integer> {
    private DbConnManager connManager;


    public ShowDataSqlRepo(Properties props) {
        connManager = new DbConnManager(props);
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(ShowData entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ShowData find(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ShowData> getAll() {
        Connection con = connManager.getConnection();
        ArrayList<ShowData> ShowDatas = new ArrayList<>();

        String queryStr = "select s.id as id, a.name as artistName,  " +
                "l.name as locationName, datetime(s.startTime) as startTime,  " +
                "cast(total(p.quantity) as integer) as soldSeats, s.availableSeats as availableSeats " +
                "from  Show as s  join Artist as a on a.id = s.artistId join Location as l " +
                "on l.id = s.locationId left join Purchase as p on p.showId = s.id group by s.id;";
        try (PreparedStatement preStmt = con.prepareStatement(queryStr)) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String artistName = result.getString("artistName");
                    String locationName = result.getString("locationName");

                    SimpleDateFormat parseD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String startTimeStr = result.getString("startTime");
                    Date startTime = parseD.parse(startTimeStr);

                    int soldSeats = result.getInt("soldSeats");
                    int availableSeats = result.getInt("availableSeats");

                    ShowDatas.add(new ShowData(id, artistName, locationName, startTime, soldSeats, availableSeats));
                }
            } catch (ParseException e) {
                throw new RepositoryException("date parse error");
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error DB " + ex);
        }
        return ShowDatas;
    }
}
