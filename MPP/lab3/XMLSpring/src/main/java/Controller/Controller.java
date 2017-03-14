package Controller;

import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validator.ClientValidator;
import Domain.Validator.MovieValidator;
import Domain.Validator.RentalValidator;
import Domain.Validator.ValidationException;
import Repository.ClientRepo;
import Repository.MovieRepo;
import Repository.RentalRepo;
import Repository.RepositoryException;
import Utils.AbstractObservable;
import Utils.Helpers;
import Utils.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Controller class
 * Provides an abstraction layer between the UI and the data layer
 */
public class Controller {
    private MovieRepo movieRepo;
    private ClientRepo clientRepo;
    private RentalRepo rentalRepo;

    private Observable<Movie> movieObservable;
    private Observable<Client> clientObservable;
    private Observable<Rental> rentalObservable;


    /**
     * Controller constructor
     * @param movieRepo  the movie Repository instance
     * @param clientRepo the client Repository instance
     */
    public Controller(MovieRepo movieRepo, ClientRepo clientRepo, RentalRepo rentalRepo) {
        this.movieRepo = movieRepo;
        this.clientRepo = clientRepo;
        this.rentalRepo = rentalRepo;
        this.movieObservable = new AbstractObservable<>();
        this.clientObservable = new AbstractObservable<>();
        this.rentalObservable = new AbstractObservable<>();
    }

    private Integer getMovieValidId() {
        Integer minId = -1;
        for (Movie mov : movieRepo.getAll()) {
            if(mov.getId() > minId)
                minId = mov.getId();
        }
        return minId + 1;
    }

    private Integer getClientValidId() {
        Integer minId = -1;
        for (Client cli : clientRepo.getAll()) {
            if(cli.getId() > minId)
                minId = cli.getId();
        }
        return minId + 1;
    }

    private Integer getRentalValidId() {
        Integer minId = -1;
        for (Rental rental : rentalRepo.getAll()) {
            if(rental.getId() > minId)
                minId = rental.getId();
        }
        return minId + 1;
    }

    public Observable<Movie> getMovieObservable() {
        return movieObservable;
    }

    public Observable<Client> getClientObservable() {
        return clientObservable;
    }

    public Observable<Rental> getRentalObservable() {
        return rentalObservable;
    }

    /**
     * Adds a client to the Repository
     * @param newClient the client data to add
     */
    public void addClient(Client newClient) throws ValidationException {
        try {
            newClient.setId(getClientValidId());
            clientRepo.add(newClient);
        } catch(RepositoryException exc) {
            throw new ValidationException("Id-ul exista deja.");
        }
        clientObservable.notifyObservers();
    }


    /**
     * Adds a movie to the Repository
     * @param newMovie the movie to add
     */
    public void addMovie(Movie newMovie) throws ValidationException{
        new MovieValidator().accept(newMovie);
        try {
            newMovie.setId(getMovieValidId());
            movieRepo.add(newMovie);
        } catch(RepositoryException exc) {
            throw new ValidationException("Id-ul exista deja.");
        }
        movieObservable.notifyObservers();
    }

    /**
     * Deletes a client given its id
     * @param id the id of the client
     * @throws ValidationException in case the id is not valid
     */
    public void deleteClient(Integer id) throws ValidationException {
        try {
            clientRepo.delete(id);
        } catch(RepositoryException exc) {
            // this means the id is not valid
            throw new ValidationException("Id-ul nu exista.");
        }
        Helpers.makeCollection(getRentals()).stream().filter(r -> r.getClientId().equals(id)).forEach(r -> deleteRental(r.getId()));
        clientObservable.notifyObservers();
    }

    public void updateClient(Client client) {
        new ClientValidator().accept(client);
        try {
            clientRepo.delete(client.getId());
            clientRepo.add(client);
        } catch(Exception exc) {
            throw new ValidationException(exc.getMessage());
        }
        clientObservable.notifyObservers();
    }

    public List<javafx.util.Pair<String, Integer>> mostRented(int count) {
        List <javafx.util.Pair <String, Integer>> result = new ArrayList<>();
        Collection<Rental> rentals = Helpers.makeCollection(getRentals());
        for(Movie mov : Helpers.makeCollection(getMovies())) {
            long cnt = rentals.stream().filter(rental -> (rental.getMovieId().equals(mov.getId()))).count(); // how many times it has been rented
            if(cnt != 0)
                result.add(new javafx.util.Pair<>(mov.getName(), (int)cnt));
        }
        return result.subList(0, count < result.size() ? count : result.size());
    }

    public void updateMovie(Movie movie) {
        new MovieValidator().accept(movie);
        try {
            movieRepo.delete(movie.getId());
            movieRepo.add(movie);
        } catch(Exception exc) {
            // this means the id is not valid
            throw new ValidationException(exc.getMessage());
        }
        movieObservable.notifyObservers();
    }

    /**
     * Delete a movie given its id
     * @param id the id of the movie
     * @throws ValidationException in case the id is not valid
     */
    public void deleteMovie(Integer id) throws ValidationException {
        try {
            movieRepo.delete(id);
        } catch(RepositoryException exc) {
            // this means the id is not valid
            throw new ValidationException("Id-ul nu exista.");
        }
        Helpers.makeCollection(getRentals()).stream().filter(r -> r.getMovieId().equals(id)).forEach(r -> deleteRental(r.getId()));
        movieObservable.notifyObservers();
    }

    public void addRental(Rental newRental) {
        validateRental(newRental);
        try {
            newRental.setId(getRentalValidId());
            rentalRepo.add(newRental);
        } catch(RepositoryException exc) {
            throw new ValidationException("Id-ul exista deja.");
        }
        rentalObservable.notifyObservers();
    }

    public void deleteRental(Integer id) throws ValidationException {
        try {
            rentalRepo.delete(id);
        } catch(RepositoryException exc) {
            // this means the id is not valid
            throw new ValidationException("Id-ul nu exista.");
        }
        rentalObservable.notifyObservers();
    }

    private void validateRental(Rental newRental) {
        new RentalValidator().accept(newRental);
        boolean movieIdExists = false;
        boolean clientIdExists = false;
        for(Movie mov : getMovies())
            if(mov.getId().equals(newRental.getMovieId()))
                movieIdExists = true;

        for(Client cli : getClients())
            if(cli.getId().equals(newRental.getClientId()))
                clientIdExists = true;

        if(!movieIdExists || !clientIdExists)
            throw new ValidationException("Movie id or client id doesn't exist.");

        for(Rental ren : getRentals())
            if(ren.getClientId().equals(newRental.getClientId()) &&
                ren.getMovieId().equals(newRental.getMovieId()))
                throw new ValidationException("Movie already rented by this user.");

    }

    /**
     * Movie getter
     * @return the list of all movies
     */
    public Iterable<Movie> getMovies() {
        return movieRepo.getAll();
    }

    /**
     * Client getter
     * @return the list of all clients
     */
    public Iterable<Client> getClients() {
        return clientRepo.getAll();
    }

    /**
     * Rental getter
     * @return the list of all rentals
     */
    public Iterable<Rental> getRentals() {
        return rentalRepo.getAll();
    }
}
