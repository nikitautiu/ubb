package Domain.Validator;


import Domain.Movie;

public class MovieValidator implements IValidator<Movie> {
    @Override
    public void accept(Movie movie) throws ValidationException {
        String errorString = "";

        if (movie.getId() < 0)
            errorString += "ID-ul trebuie sa fie pozitiv. ";

        if (movie.getDirector().equals(""))
            errorString += "Numele regizorului nu poate fi vid. ";

        if (movie.getGenre().equals(""))
            errorString += "Genul nu poate fi vid. ";

        if (movie.getName().equals(""))
            errorString += "Titlul filmului nu poate fi vid. ";

        if(!errorString.equals(""))
            throw new ValidationException(errorString);
    }
}
