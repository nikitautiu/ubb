package repository;

import model.User;

/**
 * Created by vitiv on 3/25/17.
 */
public interface IUserRepo extends ICrudRepository<User, Integer> {
    public User findByName(String name);
}
