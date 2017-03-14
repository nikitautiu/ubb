package Repository;

import Domain.Client;
import Domain.Validator.ClientValidator;

/**
 * Client repository
 * Implements an in-memory repository for clients.
 */
public class ClientRepo extends CrudRepositoryInMemory<Client, Integer> {
    /**
     * The constructor does not require any parameters
     * It just sets the validator to the client one.
     */
    public ClientRepo() {
        super(new ClientValidator());
    }
}
