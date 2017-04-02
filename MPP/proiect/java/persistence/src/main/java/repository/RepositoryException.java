package repository;

/**
 * Exception thrown by repositories
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String errorMsg) {
        super(errorMsg);
    }

}
