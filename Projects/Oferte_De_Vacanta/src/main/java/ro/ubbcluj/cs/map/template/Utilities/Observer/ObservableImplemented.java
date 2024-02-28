package ro.ubbcluj.cs.map.template.Utilities.Observer;

import java.util.ArrayList;
import java.util.List;

public class ObservableImplemented<Event> implements Observable<Event> {
    List<Observer<Event>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Event> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Event> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(Event event) {
        this.observers.forEach(observer -> observer.update(event));
    }
}
