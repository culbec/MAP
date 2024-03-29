package repository;

import domain.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Checks if the repository is empty.
     *
     * @return true if the repository is empty, false otherwise.
     */
    boolean isEmpty() throws SQLException;

    /**
     * Size of the repository.
     *
     * @return Number of entities E stored in the repository.
     */
    int size();

    /**
     * All the contents of the repository.
     *
     * @return All the values stored in the repository.
     */
    Iterable<E> getAll();

    /**
     * Searches for one entity in the repository.
     *
     * @param id ID of the Entity to search
     * @return {@code Optional}
     * - null if the entity with the specified ID does not exist
     * - otherwise returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    Optional<E> getOne(ID id) throws IllegalArgumentException;

    /**
     * Adds an entity to the repository.
     *
     * @param e Entity that should be added
     * @return an {@code Optional}
     * - null if the entity was saved
     * - the entity if it was already saved
     * @throws IllegalArgumentException If the entity is null.
     */
    Optional<E> save(E e) throws IllegalArgumentException;

    /**
     * Removes an entity from the repository
     *
     * @param id ID of the entity to remove.
     * @return an {@code Optional}
     * - null if there is no entity with the given id
     * - otherwise returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    Optional<E> delete(ID id) throws IllegalArgumentException;

    /**
     * Updates an entity.
     *
     * @param e New entity.
     * @return an {@code Optional}
     * - null if the entity was updated
     * - otherwise (e.g. id does not exist) returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    Optional<E> update(E e) throws IllegalArgumentException;
}
