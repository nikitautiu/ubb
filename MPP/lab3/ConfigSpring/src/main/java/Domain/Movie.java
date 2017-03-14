package Domain;

import java.io.Serializable;

/**
 * Domain class for movies
 * Encapsulates movie data.
 */
public class Movie implements IWithID<Integer>, Serializable {
    /**
     * Equality comparison
     * @param o the other movie to compare it with
     * @return whether the two movies are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (!getId().equals(movie.getId())) return false;
        if (!getName().equals(movie.getName())) return false;
        if (!getGenre().equals(movie.getGenre())) return false;
        return getDirector().equals(movie.getDirector());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getGenre().hashCode();
        result = 31 * result + getDirector().hashCode();
        return result;
    }

    private Integer id;
    private String name;
    private String genre;
    private String director;

    /**
     * Default constructor
     */
    public Movie(){}

    /**
     * Constructor with initialization
     * Receives values which to initialize the encapsulated data with
     * @param idFilm    the id of the movie
     * @param name      the name of the movie
     * @param genre     the genre of the movie
     * @param director  the name of the director
     */
    public Movie(int idFilm, String name, String genre, String director) {
        this.id = idFilm;
        this.name = name;
        this.genre = genre;
        this.director = director;
    }

    /**
     * Name getter
     * @return the name of the movie
     */
    public String getName() {
        return name;
    }

    /**
     * Genre getter
     * @return the genre of the movie
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Director name getter
     * @return the name of the director
     */
    public String getDirector() {
        return director;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
