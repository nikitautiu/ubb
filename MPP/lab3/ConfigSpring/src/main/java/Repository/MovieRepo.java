package Repository;

import Domain.Movie;
import Domain.Validator.MovieValidator;

/**
 * Movie Repository
 * Implements a memory repository for movies.
 */
public class MovieRepo extends CrudRepositoryInMemory<Movie, Integer> {
    /**
     * The constructor does not require any parameters
     * It just sets the validator to the movie one.
     */
    public MovieRepo() {
        super(new MovieValidator());
    }
}
