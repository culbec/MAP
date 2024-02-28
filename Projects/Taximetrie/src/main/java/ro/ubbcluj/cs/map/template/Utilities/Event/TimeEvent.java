package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Domain.Tuple;

public class TimeEvent extends AbstractEvent<Tuple<Driver, Person, Integer>>{
    public TimeEvent(EventType eventType, Tuple<Driver, Person, Integer> newEntity, Tuple<Driver, Person, Integer> oldEntity) {
        super(eventType, newEntity, oldEntity);
    }
}
