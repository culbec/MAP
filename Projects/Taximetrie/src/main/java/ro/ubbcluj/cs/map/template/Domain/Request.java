package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request extends Entity<Long> {
    private final Person person;
    private Driver driver;
    private LocalDateTime date;

    public Request(Long aLong, Person person, Driver driver, LocalDateTime date) {
        super(aLong);
        this.person = person;
        this.driver = driver;
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public Driver getDriver() {
        return driver;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Request request = (Request) o;
        return Objects.equals(person, request.person) && Objects.equals(driver, request.driver) && Objects.equals(date, request.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), person, driver, date);
    }

    @Override
    public String toString() {
        return "Request{" +
                "person=" + person +
                ", driver=" + driver +
                ", date=" + date +
                "} " + super.toString();
    }
}
