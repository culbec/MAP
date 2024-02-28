package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDBRepository extends DBRepository<Long, Driver> {
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from driver";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Long aLong) throws RepositoryException {
        String sql = "select * from driver d where d.did = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, Driver entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, Driver entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Long aLong) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, Driver entity) throws RepositoryException {
        return null;
    }

    @Override
    protected Driver extractFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("did");
        String indicativMasina = resultSet.getString("indicativ-masina");

        try(Connection connection = this.connect()) {
            String sql = "select person.username, person.name from person where person.pid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSetPerson = preparedStatement.executeQuery();

            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            return new Driver(id, username, name, indicativMasina);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    /**
     * Verifies if the username corresponds to a driver.
     * @param username Username to check.
     * @return True if the username corresponds to a driver, false otherwise.
     */
    public boolean isDriver(String username) throws RepositoryException {
        try(Connection connection = this.connect()) {
            String sql = "select * from driver inner join public.person p on p.pid = driver.did where p.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Driver loginDriver(String username) throws RepositoryException {
        try(Connection connection = this.connect()) {
            String sql = "select * from driver d inner join public.person p on p.pid = d.did where p.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return this.extractFromResultSet(resultSet);
            }
            throw new RepositoryException("The driver with the given username does not exist!");
        } catch (SQLException e) {
            throw new RepositoryException("Couldn't login user: " + e.getMessage());
        }
    }
}
