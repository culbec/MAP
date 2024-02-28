package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Location;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDBRepository extends DBRepository<Double, Location> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
         try {
             String sql = "select * from location";
             return connection.prepareStatement(sql);
         } catch (SQLException e) {
             throw new RepositoryException(e.getMessage());
         }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Double aDouble) throws RepositoryException {
        try {
            String sql = "select * from location l where l.lid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, aDouble);

            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Location extractFromResultSet(ResultSet resultSet) throws SQLException {
        double locationId = resultSet.getDouble("lid");
        String locationName = resultSet.getString("name");

        return new Location(locationId, locationName);
    }
}
