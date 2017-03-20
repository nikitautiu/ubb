package utils;

/**
 * Created by grigo on 11/16/16.
 */
public interface IObservable<E> {
    void addObserver(IObserver<E> e);
    void removeObserver(IObserver<E> e);
    void notifyObservers(E t);
}
