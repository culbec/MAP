package ro.ubbcluj.cs.map.template.Utilities;

public class Page<E> {
    private final Iterable<E> elementsOnPage;

    public Page(Iterable<E> elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public Iterable<E> getElementsOnPage() {
        return elementsOnPage;
    }
}
