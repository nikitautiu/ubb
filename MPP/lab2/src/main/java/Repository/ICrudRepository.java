package Repository;


import java.util.Collection;

/**
 * CRUD repository interface
 * Defines all the operations necessary for performing CRUD
 * on the collection of entities.
 * @param <E>   the type o the entities
 * @param <ID>  the type of the ids
 */
public interface ICrudRepository<E, ID> {
    /**
     * Size getter
     * @return the number of entities in th repository
     */
    long size();

    /**
     * Adds another entity to the repository
     * @param entity the entity to add
     * @throws RepositoryException in case the id is already in the repo
     */
    void add(E entity);

    /**
     *
     * @param id the id of the entity to delete
     * @throws RepositoryException if the id does not exist
     */
    void delete(ID id);

    /**
     * Returns the object with the given id
     * @param id the id to search for
     * @return the entity associated
     */
    E find(ID id);

    /**
     * Returns an iterable over all entities of the repo
     * @return the iterable over the repo
     */
    Collection<E> getAll();
}
