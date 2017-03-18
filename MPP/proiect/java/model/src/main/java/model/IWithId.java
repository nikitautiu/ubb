package model;

public interface IWithId<E> {
    E getId();
    void setId(E newId);
}
