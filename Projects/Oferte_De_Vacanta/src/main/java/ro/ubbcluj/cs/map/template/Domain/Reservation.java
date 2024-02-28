package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private final double reservationId;
    private final Long clientId;
    private final double hotelId;
    private final LocalDateTime startDate;
    private final int noNights;

    public Reservation(double reservationId, Long clientId, double hotelId, LocalDateTime startDate, int noNights) {
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public double getReservationId() {
        return reservationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public double getHotelId() {
        return hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public int getNoNights() {
        return noNights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Double.compare(reservationId, that.reservationId) == 0 && Double.compare(hotelId, that.hotelId) == 0 && noNights == that.noNights && Objects.equals(clientId, that.clientId) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, clientId, hotelId, startDate, noNights);
    }
}
