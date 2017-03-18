package model;

/**
 * Created by vitiv on 3/18/17.
 */
public class Purchase implements IWithId<Integer> {
    private int id;
    private int showId;
    private String clientName;
    private int quantity;

    public Purchase(int id, int showId, String clientName, int quantity) {
        this.id = id;
        this.showId = showId;
        this.clientName = clientName;
        this.quantity = quantity;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (id != purchase.id) return false;
        if (showId != purchase.showId) return false;
        if (quantity != purchase.quantity) return false;
        return clientName.equals(purchase.clientName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + showId;
        result = 31 * result + clientName.hashCode();
        result = 31 * result + quantity;
        return result;
    }
}
