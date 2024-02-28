package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.ClientDBRepository;

import java.util.Optional;

public class ServiceClient {
    private final ClientDBRepository clientDBRepository;

    public ServiceClient(ClientDBRepository clientDBRepository) {
        this.clientDBRepository = clientDBRepository;
    }

    public Client getClient(Long clientId) throws ServiceException {
        try {
            Optional<Client> optionalClient = this.clientDBRepository.getOne(clientId);
            if (optionalClient.isPresent()) {
                return optionalClient.get();
            } else {
                throw new ServiceException("Client with id " + clientId + " does not exist!");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
