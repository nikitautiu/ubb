package model;

import model.IWithId;

import java.util.Date;

/**
 * Created by vitiv on 3/20/17.
 */
public class ShowData implements IWithId<Integer> {
    private int id;
    private String artistName;
    private String locationName;
    private Date startTime;
    private int soldSeats;
    private int availableSeats;

    public ShowData(int id, String artistName, String locationName, Date startTime, int soldSeats, int availableSeats) {
        this.id = id;
        this.artistName = artistName;
        this.locationName = locationName;
        this.startTime = startTime;
        this.soldSeats = soldSeats;
        this.availableSeats = availableSeats;
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

    public int getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(int soldSeats) {
        this.soldSeats = soldSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
