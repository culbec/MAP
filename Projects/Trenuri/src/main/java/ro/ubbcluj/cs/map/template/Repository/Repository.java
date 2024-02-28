package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.util.Optional;

/**
 * CRUD Operations for the repository interface.
 *
 * @param <E>  Entity that is stored in the Repository.
 * @param <ID> Type E must have an attribute of type ID.
 */
public interface Repository<ID, E> {
    /**
     *
     * @return All the values stored in the repository.
     */
    Iterable<E> getAll() throws RepositoryException;

    /**
     * Returns an entity from the repository.
     * @param id ID of the entity to be returned.
     * @return Optional containing the entity if it was found, null otherwise.
     * @throws IllegalArgumentException If the given ID is null.
     * @throws RepositoryException If a problem was encountered during the operation.
     */
    Optional<E> getOne(ID id) throws IllegalArgumentException, RepositoryException;
}