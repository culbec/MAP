package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class TrainStation {
    private final String trainId;
    private final String departureCityId;
    private final String destinationCityId;

    public TrainStation(String trainId, String departureCityId, String destinationCityId) {
        this.trainId = trainId;
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getDepartureCityId() {
        return departureCityId;
    }

    public String getDestinationCityId() {
        return destinationCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainStation that = (TrainStation) o;
        return Objects.equals(trainId, that.trainId) && Objects.equals(departureCityId, that.departureCityId) && Objects.equals(destinationCityId, that.destinationCityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, departureCityId, destinationCityId);
    }

    @Override
    public String toString() {
        return "TrainStation{" +
                "trainId='" + trainId + '\'' +
                ", departureCityId='" + departureCityId + '\'' +
                ", destinationCityId='" + destinationCityId + '\'' +
                '}';
    }
}
