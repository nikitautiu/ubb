package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archie on 12/8/2016.
 */
public class AbstractObservable<E> implements Observable<E> {
    private List<Observer<E>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<E> o) {
        System.out.println("Add observer" + o.getClass());
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<E> o) {
        System.out.println("Remove observer" + o.getClass());
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}
