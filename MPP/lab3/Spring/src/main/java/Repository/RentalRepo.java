package Repository;

import Domain.Rental;
import Domain.Validator.RentalValidator;


public class RentalRepo extends CrudRepositoryInMemory<Rental, Integer> {
    public RentalRepo() {
        super(new RentalValidator());
    }
}
