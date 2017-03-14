package Repository;

import Domain.IWithID;
import Domain.Validator.IValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * In memory repository
 * Implements the CRUD repository interface
 * @param <E>  the type of the entities
 * @param <ID> the type of the ids
 */
public abstract class CrudRepositoryInMemory<E extends IWithID<ID>, ID> implements ICrudRepository<E, ID> {
    protected List<E> entities = new ArrayList<>();
    private IValidator<E> validator;

    public CrudRepositoryInMemory(IValidator<E> validator) {
        this.validator = validator;
    }

    @Override
    public long size() {
        return entities.size();
    }

    @Override
    public void add(E entity) {
        this.validator.accept(entity);

        if(find(entity.getId()) != null)
            delete(entity.getId());
        entities.add(entity);
    }

    @Override
    public void delete(ID id) {
        Iterator<E> it = entities.iterator();
        while (it.hasNext()) {
            E current = it.next();
            if (current.getId().equals(id)) {
                entities.remove(current);
                return;
            }
        }
        throw new RepositoryException("Id not found.");
    }

    @Override
    public E find(ID id) {
        Iterator<E> it = entities.iterator();
        while (it.hasNext()) {
            E current = it.next();
            if (current.getId().equals(id))
                return current;
        }
        return null;
    }

    @Override
    public Iterable<E> getAll() {
        return new ArrayList<E>(entities);
    }
}
