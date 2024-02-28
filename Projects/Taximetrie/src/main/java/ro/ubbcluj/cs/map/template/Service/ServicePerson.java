package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.PersonDBRepository;

public class ServicePerson {
    private final PersonDBRepository personDBRepository;

    public ServicePerson(PersonDBRepository personDBRepository) {
        this.personDBRepository = personDBRepository;
    }

    public boolean isPerson(String username) {
        return this.personDBRepository.isPerson(username);
    }

    public Person loginPerson(String username) {
        try {
            return this.personDBRepository.loginPerson(username);
        } catch (RepositoryException e) {
            throw new ServiceException("Something went wrong: " + e.getMessage());
        }
    }
}
