package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.PersonDTO;

public class SentRequestEvent extends AbstractEvent<PersonDTO> {
    public SentRequestEvent(EventType eventType, PersonDTO newEntity, PersonDTO oldEntity) {
        super(eventType, newEntity, oldEntity);
    }
}
