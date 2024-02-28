package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.FlightDTO;

public class TicketEvent extends AbstractEvent<FlightDTO> {
    public TicketEvent(EventType eventType, FlightDTO newEntity, FlightDTO oldEntity) {
        super(eventType, newEntity, oldEntity);
    }
}
