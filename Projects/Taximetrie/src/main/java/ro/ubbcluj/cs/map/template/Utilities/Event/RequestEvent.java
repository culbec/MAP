package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.Request;

public class RequestEvent extends AbstractEvent<Request>{
    public RequestEvent(EventType eventType, Request newEntity, Request oldEntity) {
        super(eventType, newEntity, oldEntity);
    }
}
