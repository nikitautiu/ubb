package Utils;

/**
 * Created by Sergiu on 11/8/2016.
 */
public interface Observable<E> {
    void addObserver(Observer<E> o);
    void removeObserver(Observer<E> o);
    void notifyObservers();
}
