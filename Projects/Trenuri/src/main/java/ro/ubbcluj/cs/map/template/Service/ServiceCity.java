package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.City;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.CityDBRepository;

import java.util.List;
import java.util.Optional;

public class ServiceCity {
    private final CityDBRepository cityDBRepository;

    public ServiceCity(CityDBRepository cityDBRepository) {
        this.cityDBRepository = cityDBRepository;
    }

    /**
     *
     * @return All the cities contained in the repository.
     */
    public List<City> getCities() {
        return (List<City>) cityDBRepository.getAll();
    }

    /**
     * Returns a city based on its ID.
     * @param id ID of the city to be returned.
     * @return City with the given ID.
     * @throws ServiceException If the city does not exist.
     */
    public City getCity(String id) throws ServiceException {
        Optional<City> city = cityDBRepository.getOne(id);
        if (city.isPresent()) {
            return city.get();
        }
        throw new ServiceException("City does not exist!");
    }
}
