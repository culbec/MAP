package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDBRepository extends DBRepository<Long, Person>{
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from person";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Long aLong) throws RepositoryException {
        String sql = "select * from person where person.pid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, Person entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, Person entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Long aLong) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, Person entity) throws RepositoryException {
        return null;
    }

    @Override
    protected Person extractFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("pid");
        String username = resultSet.getString("username");
        String name = resultSet.getString("name");

        return new Person(id, username, name);
    }

    /**
     * Verifies if the given username corresponds to a person.
     * @param username Username of the person to check.
     * @return True if the username corresponds to a person, false otherwise.
     */
    public boolean isPerson(String username) throws RepositoryException {
        try(Connection connection = this.connect()) {
            String sql = "select * from person where person.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Person loginPerson(String username) throws RepositoryException {
        try(Connection connection = this.connect()) {
            String sql = "select * from person p where p.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return this.extractFromResultSet(resultSet);
            }
            throw new RepositoryException("The person with the given username does not exist!");
        } catch (SQLException e) {
            throw new RepositoryException("Couldn't login user: " + e.getMessage());
        }
    }
}
