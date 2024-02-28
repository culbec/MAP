package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class Location {
    private final double locationId;
    private final String locationName;

    public Location(double locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public double getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(locationId, location.locationId) == 0 && Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, locationName);
    }

    @Override
    public String toString() {
        return this.locationName;
    }
}
