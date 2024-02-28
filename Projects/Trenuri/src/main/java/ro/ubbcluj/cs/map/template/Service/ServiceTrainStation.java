package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Controller.ClientController;
import ro.ubbcluj.cs.map.template.Domain.City;
import ro.ubbcluj.cs.map.template.Domain.TrainStation;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.TrainStationDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.SelectionEvent;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.RouteFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceTrainStation extends ObservableImplemented<SelectionEvent> {
    private final TrainStationDBRepository trainStationDBRepository;

    public ServiceTrainStation(TrainStationDBRepository trainStationDBRepository) {
        this.trainStationDBRepository = trainStationDBRepository;
    }

    /**
     *
     * @return All the train stations contained in the repository.
     */
    public Iterable<TrainStation> getTrainStations() {
        return trainStationDBRepository.getAll();
    }

    /**
     * Returns all the direct routes between two cities.
     * @param departureCityId ID of the departure city.
     * @param destinationCityId ID of the destination city.
     * @return List of train stations that represent the direct routes between the two cities.
     */
    public List<List<TrainStation>> getRoutesBetweenCities(String departureCityId, String destinationCityId) {
        List<TrainStation> trainStations = (List<TrainStation>) this.getTrainStations();
        List<List<TrainStation>> routes = RouteFinder.findRoutes(departureCityId, destinationCityId, trainStations);

        return routes.stream()
                .filter(route -> {
                    String trainId = route.get(0).getTrainId();
                    for (int i = 1; i < route.size(); i++) {
                        if (!route.get(i).getTrainId().equals(trainId)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }

    /**
     * Computes the number of viewers that have applied the same filters.
     */
    public void computeViewers() {
        Map<Tuple<City, City>, Integer> cities = new HashMap<>();

        this.observers.forEach(observer -> {
            City departureCity = ((ClientController) observer).getDepartureCity();
            City destinationCity = ((ClientController) observer).getDestinationCity();

            Tuple<City, City> tuple = new Tuple<>(departureCity, destinationCity);
            if (cities.containsKey(tuple)) {
                cities.put(tuple, cities.get(tuple) + 1);
            } else {
                cities.put(tuple, 0);
            }
        });

        this.notify(new SelectionEvent(EventType.RECALCULATE_VIEWERS, cities));
    }

    /**
     * Returns all the trains that depart from a given city.
     * @param departureCityId ID of the city.
     * @return List of trains that depart from the given city.
     * @throws ServiceException If a problem was encountered during the operation.
     */
    public List<String> getTrainsFromCity(String departureCityId) throws ServiceException {
        try {
            return this.trainStationDBRepository.getTrainsFromCity(departureCityId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
