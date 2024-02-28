package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class FlightDTO {
    private final Flight flight;
    private final int numberOfTickets;

    public FlightDTO(Flight flight, int numberOfTickets) {
        this.flight = flight;
        this.numberOfTickets = numberOfTickets;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDTO flightDTO = (FlightDTO) o;
        return numberOfTickets == flightDTO.numberOfTickets && Objects.equals(flight, flightDTO.flight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, numberOfTickets);
    }
}
