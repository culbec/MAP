package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Flight;
import ro.ubbcluj.cs.map.template.Repository.FlightDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceFlight {
    private final FlightDBRepository flightDBRepository;

    public ServiceFlight(FlightDBRepository flightDBRepository) {
        this.flightDBRepository = flightDBRepository;
    }

    public List<String> getDepartureLocations() {
        return this.flightDBRepository.getDepartureLocations();
    }

    public List<String> getLandingLocations() {
        return this.flightDBRepository.getLandingLocations();
    }

    public List<Flight> getFlightsFromToOnDate(String from, String to, LocalDateTime departureTime, Pageable pageable) {
        return this.flightDBRepository.getFlightsFromToOnDate(from, to, departureTime, pageable);
    }
}
