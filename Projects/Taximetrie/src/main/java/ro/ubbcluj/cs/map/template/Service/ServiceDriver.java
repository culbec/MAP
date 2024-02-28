package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.DriverDBRepository;

public class ServiceDriver {
    private final DriverDBRepository driverDBRepository;

    public ServiceDriver(DriverDBRepository driverDBRepository) {
        this.driverDBRepository = driverDBRepository;
    }

    public boolean isDriver(String username) {
        return this.driverDBRepository.isDriver(username);
    }

    public Driver loginDriver(String username) {
        try {
            return this.driverDBRepository.loginDriver(username);
        } catch (RepositoryException e) {
            throw new ServiceException("Something went wrong: " + e.getMessage());
        }
    }
}
