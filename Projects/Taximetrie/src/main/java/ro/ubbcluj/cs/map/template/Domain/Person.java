package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class Person extends Entity<Long>{
    private final String username;
    private final String name;

    public Person(Long aLong, String username, String name) {
        super(aLong);
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username) && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
