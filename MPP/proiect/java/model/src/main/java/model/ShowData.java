package model;

import java.util.Date;

/**
 * Created by vitiv on 3/20/17.
 */
public class ShowData implements IWithId<Integer> {
    private int remainingSeats;
    private int id;
    private String artistName;
    private String locationName;
    private Date startTime;


    public ShowData(int id, String artistName, String locationName, Date startTime, int remainingSeats) {
        this.id = id;
        this.artistName = artistName;
        this.locationName = locationName;
        this.startTime = startTime;
        this.remainingSeats = remainingSeats;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }
}
