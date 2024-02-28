package ro.ubbcluj.cs.map.template.Utilities.Event;

public class AbstractEvent<E> {
    private final EventType eventType;
    private final E newEntity;
    private final E oldEntity;

    public AbstractEvent(EventType eventType, E newEntity, E oldEntity) {
        this.eventType = eventType;
        this.newEntity = newEntity;
        this.oldEntity = oldEntity;
    }

    public EventType getEventType() {
        return eventType;
    }

    public E getNewEntity() {
        return newEntity;
    }

    public E getOldEntity() {
        return oldEntity;
    }
}
