package Domain;

/**
 * Interface for a ny type of object that defines an id
 * @param <E> the type of the id
 */
public interface IWithID<E> {
    E getId();
    void setId(E newId);
}