package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientDBRepository extends DBRepository<String, Client>{
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from client";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, String string) throws RepositoryException {
        String sql = "select * from client c where c.username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, string);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Client extractFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("username");
        String name = resultSet.getString("name");

        return new Client(username, name);
    }

    public Optional<Client> loginClient(String username) throws RepositoryException {
        try(Connection connection = this.connect()) {
            String sql = "select * from client c where c.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? Optional.of(this.extractFromResultSet(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
