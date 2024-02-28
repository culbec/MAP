package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Utilities.Constants;
import ro.ubbcluj.cs.map.template.Domain.Entity;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public abstract class DBRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private static final String DB_URL = Constants.DB_URL;
    private static final String DB_USER = Constants.DB_USER;
    private static final String DB_PASSWORD = Constants.DB_PASSWORD;

    /**
     * Returns the SQL Interrogation for counting the rows in a table.
     *
     * @return SQL Interrogation for counting the rows in a table.
     */
    protected abstract PreparedStatement statementCount(Connection connection) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for selecting all entries in a table.
     *
     * @return SQL Interrogation for selecting all entries in a table.
     */
    protected abstract PreparedStatement statementSelectAll(Connection connection) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for selection by ID.
     *
     * @param id ID on which the interrogation will proceed.
     * @return SQL Interrogation for selection by ID.
     */
    protected abstract PreparedStatement statementSelectOnID(Connection connection, ID id) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for selection by fields.
     *
     * @param entity Entity on which the interrogation will proceed.
     * @return SQL Interrogation for selection by fields.
     */
    protected abstract PreparedStatement statementSelectOnFields(Connection connection, E entity) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for inserting into a table.
     *
     * @param entity Entity on which the interrogation will proceed.
     * @return SQL Interrogation for inserting into a table.
     */
    protected abstract PreparedStatement statementInsert(Connection connection, E entity) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for deleting from a table.
     *
     * @param id ID on which the interrogation will proceed.
     * @return SQL Interrogation for deleting from a table.
     */
    protected abstract PreparedStatement statementDelete(Connection connection, ID id) throws RepositoryException;

    /**
     * Returns the SQL Interrogation for updating rows in a table.
     *
     * @param entity Entity on which the interrogation will proceed.
     * @return SQL Interrogation for updating rows in table.
     */
    protected abstract PreparedStatement statementUpdate(Connection connection, E entity) throws RepositoryException;

    /**
     * Connects to the database.
     *
     * @return A connection to the database.
     */
    public Connection connect() throws RepositoryException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    /**
     * Extracts an Entity from a given result set.
     *
     * @param resultSet Given result set.
     * @return Entity extracted from the result set.
     * @throws SQLException Resulted from the extraction if a problem was encountered.
     */
    protected abstract E extractFromResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public boolean isEmpty() throws RepositoryException {
        return this.size() == 0;
    }

    @Override
    public int size() throws RepositoryException {
        try (Connection connection = this.connect()) {
            try (PreparedStatement statement = this.statementCount(connection)) {
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return 0;
    }

    @Override
    public Iterable<E> getAll() throws RepositoryException {
        List<E> entities = new ArrayList<>();

        try (Connection connection = this.connect()) {
            try (PreparedStatement statement = this.statementSelectAll(connection)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    E entity = this.extractFromResultSet(resultSet);
                    entities.add(entity);
                }
            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<E> getOne(ID id) throws IllegalArgumentException, RepositoryException {
        if (id == null) {
            throw new IllegalArgumentException("The id cannot be null!");
        }

        try (Connection connection = this.connect()) {
            try (PreparedStatement statement = this.statementSelectOnID(connection, id)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(this.extractFromResultSet(resultSet));
                }
            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<E> save(E entity) throws RepositoryException, IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }

        try (Connection connection = this.connect()) {
            try (PreparedStatement statementSelect = this.statementSelectOnFields(connection, entity)) {
                ResultSet resultSetSelect = statementSelect.executeQuery();

                if (resultSetSelect.next()) {
                    E _entity = this.extractFromResultSet(resultSetSelect);
                    return Optional.of(_entity);
                }

                try (PreparedStatement statementInsert = this.statementInsert(connection, entity)) {
                    statementInsert.execute();
                } catch (SQLException sqlException) {
                    throw new RepositoryException(sqlException.getMessage());
                }

            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<E> delete(ID id) throws RepositoryException, IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        }

        try (Connection connection = this.connect()) {
            try (PreparedStatement statementSelect = this.statementSelectOnID(connection, id)) {
                ResultSet resultSetSelect = statementSelect.executeQuery();
                if (resultSetSelect.next()) {
                    try (PreparedStatement statementDelete = this.statementDelete(connection, id)) {
                        Optional<E> deleted = Optional.of(this.extractFromResultSet(resultSetSelect));
                        statementDelete.execute();
                        return deleted;
                    } catch (SQLException sqlException) {
                        throw new RepositoryException(sqlException.getMessage());
                    }
                }
            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<E> update(E entity) throws RepositoryException, IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        }

        try (Connection connection = this.connect()) {
            try (PreparedStatement statementSelect = this.statementSelectOnID(connection, entity.getId())) {
                ResultSet resultSetSelect = statementSelect.executeQuery();

                if (resultSetSelect.next()) {
                    try (PreparedStatement statementUpdate = this.statementUpdate(connection, entity)) {
                        Optional<E> old = Optional.of(this.extractFromResultSet(resultSetSelect));
                        statementUpdate.execute();
                        return old;
                    } catch (SQLException sqlException) {
                        throw new RepositoryException(sqlException.getMessage());
                    }
                }
            } catch (SQLException sqlException) {
                throw new RepositoryException(sqlException.getMessage());
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return Optional.empty();
    }

}
