package model;

import java.util.Date;

/**
 * Created by vitiv on 3/18/17.
 */
public class Show implements IWithId<Integer> {
    private int id;
    private int artistId;
    private int locationId;
    private Date startTime;
    private int availableSeats;


    public Show(int id, int artistId, int locationId, Date startTime, int availableSeats) {
        this.id = id;
        this.artistId = artistId;
        this.locationId = locationId;
        this.startTime = startTime;
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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (id != show.id) return false;
        if (artistId != show.artistId) return false;
        if (availableSeats != show.availableSeats) return false;
        return startTime.equals(show.startTime);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + artistId;
        result = 31 * result + startTime.hashCode();
        result = 31 * result + availableSeats;
        return result;
    }
}
