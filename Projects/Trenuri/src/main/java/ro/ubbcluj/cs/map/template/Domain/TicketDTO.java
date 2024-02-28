package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class TicketDTO {
    private final String cityName;
    private final String trainId;
    private final int noTickets;

    public TicketDTO(String cityName, String trainId, int noTickets) {
        this.cityName = cityName;
        this.trainId = trainId;
        this.noTickets = noTickets;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTrainId() {
        return trainId;
    }

    public int getNoTickets() {
        return noTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return noTickets == ticketDTO.noTickets && Objects.equals(cityName, ticketDTO.cityName) && Objects.equals(trainId, ticketDTO.trainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, trainId, noTickets);
    }
}
