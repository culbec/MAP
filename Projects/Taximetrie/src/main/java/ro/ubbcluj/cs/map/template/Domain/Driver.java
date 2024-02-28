package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class Driver extends Person {
    private final String indicativMasina;

    public Driver(Long aLong, String username, String name, String indicativMasina) {
        super(aLong, username, name);
        this.indicativMasina = indicativMasina;
    }

    public String getIndicativMasina() {
        return indicativMasina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(indicativMasina, driver.indicativMasina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), indicativMasina);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "indicativMasina='" + indicativMasina + '\'' +
                "} " + super.toString();
    }
}
