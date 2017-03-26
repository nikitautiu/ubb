package model;

/**
 * Created by vitiv on 3/18/17.
 */
public class User implements IWithId<Integer> {
    private int id;
    private String name;
    private String passHash;

    public User(int id, String name, String passHash) {
        this.id = id;
        this.name = name;
        this.passHash = passHash;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return passHash != null ? passHash.equals(user.passHash) : user.passHash == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        return result;
    }
}
