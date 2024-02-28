package ro.ubbcluj.cs.map.template.Utilities;

import ro.ubbcluj.cs.map.template.Domain.TrainStation;

import java.util.ArrayList;
import java.util.List;

public class RouteFinder {
    /**
     * Finds all routes between two cities.
     * @param departureCityId ID of the departure city.
     * @param destinationCityId ID of the destination city.
     * @param trainStations List of all train stations.
     * @return List of all routes between the two cities.
     */
    public static List<List<TrainStation>> findRoutes(String departureCityId, String destinationCityId, List<TrainStation> trainStations) {
        List<List<TrainStation>> routes = new ArrayList<>();

        // Find all stations that have the departure city as the current city.
        List<TrainStation> departureStations = trainStations.stream()
                .filter(trainStation -> trainStation.getDepartureCityId().equals(departureCityId))
                .toList();

        // For each station, find all routes that start from that station.
        departureStations.forEach(departureStation -> {
            List<TrainStation> currentRoute = new ArrayList<>();
            currentRoute.add(departureStation);
            routes.addAll(findRoutesFromCity(destinationCityId, currentRoute, trainStations));
        });

        return routes;
    }

    /**
     * Finds all routes that start from a given city.
     * @param destinationCityId ID of the destination city.
     * @param currentRoute Current route.
     * @param trainStations List of all train stations.
     * @return List of all routes that start from the given city.
     */
    public static List<List<TrainStation>> findRoutesFromCity(String destinationCityId, List<TrainStation> currentRoute, List<TrainStation> trainStations) {
        List<List<TrainStation>> routes = new ArrayList<>();

        // Check if the current city is the destination.
        if (currentRoute.get(currentRoute.size() - 1).getDestinationCityId().equals(destinationCityId)) {
            routes.add(new ArrayList<>(currentRoute));
        } else {
            // Explore all stations starting from the last destination from the 'current route'.
            List<TrainStation> nextStations = trainStations.stream()
                    .filter(station -> station.getDepartureCityId().equals(currentRoute.get(currentRoute.size() - 1).getDestinationCityId()))
                    .toList();

            // For each station, find all routes that start from that station.
            for (TrainStation nextStation : nextStations) {
                if (!currentRoute.contains(nextStation)) {
                    currentRoute.add(nextStation);
                    routes.addAll(findRoutesFromCity(destinationCityId, currentRoute, trainStations));
                    currentRoute.remove(currentRoute.size() - 1); // Backtrack
                }
            }
        }

        return routes;
    }
}
