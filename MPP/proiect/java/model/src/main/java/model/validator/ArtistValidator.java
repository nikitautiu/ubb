package model.validator;

import model.Artist;
import model.Purchase;

/**
 * Created by vitiv on 5/30/17.
 */
public class ArtistValidator implements IValidator<Artist> {
    @Override
    public void accept(Artist artist) {
        String errorString = "";
        if (artist.getId() != null && artist.getId() < 0)
            errorString += "Id must be positive.";
        if (artist.getName().equals(""))
            errorString += "Name must be non-empty.";
        if(!errorString.equals(""))
            throw new ValidationException(errorString);
    }
}
