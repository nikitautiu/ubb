package Utils;

/**
 * Created by Sergiu on 11/8/2016.
 */
public interface Observer<E> {
    void update(Observable<E> e);
}
