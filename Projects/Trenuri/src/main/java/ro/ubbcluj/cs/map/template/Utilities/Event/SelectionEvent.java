package ro.ubbcluj.cs.map.template.Utilities.Event;

import ro.ubbcluj.cs.map.template.Domain.City;
import ro.ubbcluj.cs.map.template.Domain.Tuple;

import java.util.Map;

public class SelectionEvent {
    private final EventType eventType;
    private final Map<Tuple<City, City>, Integer> routes;

    public SelectionEvent(EventType eventType, Map<Tuple<City, City>, Integer> routes) {
        this.eventType = eventType;
        this.routes = routes;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Map<Tuple<City, City>, Integer> getRoutes() {
        return routes;
    }
}
