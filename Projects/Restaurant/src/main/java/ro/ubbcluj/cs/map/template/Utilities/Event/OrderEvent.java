package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.Order;

public class OrderEvent extends AbstractEvent<Order> {
    public OrderEvent(EventType eventType, Order newEntity, Order oldEntity) {
        super(eventType, newEntity, oldEntity);
    }
}
