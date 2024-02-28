package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.TrainStation;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainStationDBRepository extends DBRepository<String, TrainStation> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from train_stations";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, String string) throws RepositoryException {
        try {
            String sql = "select * from train_stations ts where ts.tid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, string);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected TrainStation extractFromResultSet(ResultSet resultSet) throws SQLException {
        String trainId = resultSet.getString("trainId");
        String departureCityId = resultSet.getString("departureCityId");
        String destinationCityId = resultSet.getString("destinationCityId");

        return new TrainStation(trainId, departureCityId, destinationCityId);
    }

    /**
     * Returns all the trains that depart from a given city.
     * @param departureCityId ID of the city.
     * @return List of trains that depart from the given city.
     * @throws RepositoryException If a problem was encountered during the operation.
     */
    public List<String> getTrainsFromCity(String departureCityId) throws RepositoryException {
        try (Connection connection = this.connect()) {
            String sql = "select distinct t.\"trainId\" from train_stations t where t.\"departureCityId\" = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, departureCityId);
                ResultSet resultSet = statement.executeQuery();

                List<String> trains = new ArrayList<>();
                while (resultSet.next()) {
                    trains.add(resultSet.getString("trainId"));
                }

                return trains;
            } catch (SQLException e) {
                throw new RepositoryException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
