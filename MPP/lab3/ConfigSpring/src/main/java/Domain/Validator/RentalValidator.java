package Domain.Validator;

import Domain.Rental;

/**
 * Created by archie on 12/22/2016.
 */
public class RentalValidator implements IValidator<Rental> {
    @Override
    public void accept(Rental rental) {
        // pretty much the same validation as the other entities
        String errorString = "";
        if (rental.getId() < 0)
            errorString += "ID-ul trebuie sa fie pozitiv. ";
        if (rental.getMovieId() < 0)
            errorString += "ID-ul filmului trebuie sa fie pozitiv. ";
        if (rental.getClientId() < 0)
            errorString += "ID-ul clientului trebuie sa fie pozitiv. ";
        if(!errorString.equals(""))
            throw new ValidationException(errorString);
    }
}
