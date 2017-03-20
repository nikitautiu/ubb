package model.validator;

import java.util.function.Consumer;;

/**
 * Validator interface
 * Defines a method which validates an object and returns errors.
 *
 * @param <E> the type of the objects to validate
 */

public interface IValidator<E> extends Consumer<E> {
    /**
     * Receives an object and returns potential validation errors
     * If a blank string is returned, it means that the object was valid.
     * @param entity the object to validate
     * @throws ValidationException in case validation fails with the validation erros as the message
     */
}
