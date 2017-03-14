package Domain.Validator;

/**
 * Custom exception for all errors related to validation
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String errorMsg) {
        super(errorMsg);
    }
}
