package Domain;


import java.io.Serializable;

/**
 * Domain class for the clients
 * Encapsulates the data of the clients.
 */
public class Client implements IWithID<Integer>, Serializable {
    private Integer id;
    private String name;

    /**
     * Default constructor ot the Client class
     */
    public Client() {}

    /**
     * Client constructor with initialization
     * @param id    the id to initialize the client with
     * @param name  the name of the client
     */
    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Name getter
     * @return the name of the client
     */
    public String getName() {
        return name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Equality comparison
     * @param other the object to compare it to
     * @return  whether the clients are identical
     */
    public boolean equals(Client other){
        return this.getId() == other.getId() && this.getName() == other.getName();
    }

    /**
     * String representation
     * @return  the string representation of the client
     */
    @Override
    public String toString() {
        return name;
    }
}
