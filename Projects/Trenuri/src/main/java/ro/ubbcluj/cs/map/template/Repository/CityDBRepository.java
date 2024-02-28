package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.City;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDBRepository extends DBRepository<String, City> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try{
            String sql = "select * from cities";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, String string) throws RepositoryException {
        try {
            String sql = "select * from cities c where c.cid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, string);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected City extractFromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("cid");
        String name = resultSet.getString("name");

        return new City(id, name);
    }
}
