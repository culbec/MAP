package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private final String username;
    private final Long flightId;
    private final LocalDateTime purchaseTime;

    public Ticket(String username, Long flightId, LocalDateTime purchaseTime) {
        this.username = username;
        this.flightId = flightId;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername() {
        return username;
    }

    public Long getFlightId() {
        return flightId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(username, ticket.username) && Objects.equals(flightId, ticket.flightId) && Objects.equals(purchaseTime, ticket.purchaseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, flightId, purchaseTime);
    }
}
