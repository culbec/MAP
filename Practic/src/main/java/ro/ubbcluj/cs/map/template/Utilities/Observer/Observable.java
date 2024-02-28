package ro.ubbcluj.cs.map.template.Utilities.Observer;

public interface Observable<E> {
    void addObserver(Observer<E> observer);
    void removeObserver(Observer<E> observer);
    void notify(E e);
}
