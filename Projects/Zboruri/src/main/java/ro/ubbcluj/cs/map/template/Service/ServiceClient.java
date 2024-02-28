package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.ClientDBRepository;

import java.util.List;
import java.util.Optional;

public class ServiceClient {
    private final ClientDBRepository clientDBRepository;

    public ServiceClient(ClientDBRepository clientDBRepository) {
        this.clientDBRepository = clientDBRepository;
    }

    public Client loginClient(String username) throws ServiceException {
        try {
            Optional<Client> clientOptional = this.clientDBRepository.loginClient(username);

            if (clientOptional.isPresent()) {
                return clientOptional.get();
            }
            throw new ServiceException("The user with the specified username does not exist!");
        } catch (RepositoryException e) {
            throw new ServiceException("Cause: " + e.getMessage());
        }
    }
}
