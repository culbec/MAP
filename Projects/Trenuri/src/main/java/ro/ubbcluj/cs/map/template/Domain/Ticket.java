package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDate;
import java.util.Objects;

public class Ticket {
    private final String trainId;
    private final String departureCityId;
    private final LocalDate date;

    public Ticket(String trainId, String departureCityId, LocalDate date) {
        this.trainId = trainId;
        this.departureCityId = departureCityId;
        this.date = date;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getDepartureCityId() {
        return departureCityId;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(trainId, ticket.trainId) && Objects.equals(departureCityId, ticket.departureCityId) && Objects.equals(date, ticket.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, departureCityId, date);
    }
}
