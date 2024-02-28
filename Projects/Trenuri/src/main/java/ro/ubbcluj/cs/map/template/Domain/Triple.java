package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class Triple<E1, E2, E3> {
    private final E1 e1;
    private final E2 e2;
    private final E3 e3;

    public Triple(E1 e1, E2 e2, E3 e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public E1 getFirst() {
        return this.e1;
    }

    public E2 getSecond() {
        return this.e2;
    }

    public E3 getThird() {
        return this.e3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(e1, triple.e1) && Objects.equals(e2, triple.e2) && Objects.equals(e3, triple.e3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2, e3);
    }
}
