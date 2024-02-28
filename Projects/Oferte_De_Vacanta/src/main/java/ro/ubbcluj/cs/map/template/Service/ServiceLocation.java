package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Location;
import ro.ubbcluj.cs.map.template.Repository.LocationDBRepository;

import java.util.List;

public class ServiceLocation {
    private final LocationDBRepository locationDBRepository;

    public ServiceLocation(LocationDBRepository locationDBRepository) {
        this.locationDBRepository = locationDBRepository;
    }

    public Location getLocation(Double locationId) {
        return this.locationDBRepository.getOne(locationId).orElse(null);
    }

    public List<Location> getLocations() {
        return (List<Location>) this.locationDBRepository.getAll();
    }
}
