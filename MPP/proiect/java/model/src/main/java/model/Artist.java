package model;

/**
 * Created by vitiv on 3/18/17.
 */
public class Artist implements IWithId<Integer> {
    private Integer id;
    private String name;

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Artist() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        return name.equals(artist.name);
    }

}
