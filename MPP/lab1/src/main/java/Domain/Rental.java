package Domain;

/**
 * Created by archie on 12/22/2016.
 */
public class Rental implements IWithID<Integer> {
    private Integer id;
    private Integer movieId;
    private Integer clientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;

        Rental rental = (Rental) o;

        if (!getId().equals(rental.getId())) return false;
        if (!getMovieId().equals(rental.getMovieId())) return false;
        return getClientId().equals(rental.getClientId());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getMovieId().hashCode();
        result = 31 * result + getClientId().hashCode();
        return result;
    }

    public Rental(int id, int idMovie, int idClient) {
        this.id = id;
        this.movieId = idMovie;
        this.clientId = idClient;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
