package ro.ubbcluj.cs.map.template.Utilities.Event;

public class AbstractEvent<E> {
    private final EventType eventType;
    private final E oldEntity;
    private final E newEntity;

    public AbstractEvent(EventType eventType, E oldEntity, E newEntity) {
        this.eventType = eventType;
        this.oldEntity = oldEntity;
        this.newEntity = newEntity;
    }

    public EventType getEventType() {
        return eventType;
    }

    public E getOldEntity() {
        return oldEntity;
    }

    public E getNewEntity() {
        return newEntity;
    }
}
