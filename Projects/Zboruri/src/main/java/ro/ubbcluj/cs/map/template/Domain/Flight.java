package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight extends Entity<Long> {
    private final String from;
    private final String to;
    private final LocalDateTime departureTime;
    private final LocalDateTime landingTime;
    private final int seats;

    public Flight(Long aLong, String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, int seats) {
        super(aLong);
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return seats == flight.seats && Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(landingTime, flight.landingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, departureTime, landingTime, seats);
    }
}
