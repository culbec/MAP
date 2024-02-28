package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.Reservation;

public class ReservationEvent {
    private final EventType eventType;
    private final Reservation reservation;

    public ReservationEvent(EventType eventType, Reservation reservation) {
        this.eventType = eventType;
        this.reservation = reservation;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
