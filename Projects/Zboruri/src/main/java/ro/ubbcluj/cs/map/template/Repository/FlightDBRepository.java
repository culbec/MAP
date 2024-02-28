package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Flight;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

public class FlightDBRepository extends DBRepository<Long, Flight> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from flight";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Long aLong) throws RepositoryException {
        String sql = "select * from flight f where f.fid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Flight extractFromResultSet(ResultSet resultSet) throws SQLException {
        Long flightId = resultSet.getLong("fid");
        String from = resultSet.getString("from");
        String to = resultSet.getString("to");
        LocalDateTime departureTime = resultSet.getTimestamp("departure-time").toLocalDateTime();
        LocalDateTime landingTime = resultSet.getTimestamp("landing-time").toLocalDateTime();
        int seats = resultSet.getInt("seats");

        return new Flight(flightId, from, to, departureTime, landingTime, seats);
    }

    public List<Flight> getFlightsFromToOnDate(String from, String to, LocalDateTime departureTime, Pageable pageable) {
        return StreamSupport.stream(this.getAll().spliterator(), false)
                .filter(flight -> flight.getFrom().equals(from) && flight.getTo().equals(to) &&
                        flight.getDepartureTime().getDayOfYear() == departureTime.getDayOfYear() &&
                        flight.getDepartureTime().getMonth().getValue() == departureTime.getMonth().getValue() &&
                        flight.getDepartureTime().getYear() == departureTime.getYear())
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .toList();
    }

    public List<String> getDepartureLocations() {
        Iterable<Flight> flights = this.getAll();

        return StreamSupport.stream(flights.spliterator(), false)
                .map(Flight::getFrom)
                .distinct()
                .toList();
    }

    public List<String> getLandingLocations() {
        Iterable<Flight> flights = this.getAll();

        return StreamSupport.stream(flights.spliterator(), false)
                .map(Flight::getTo)
                .distinct()
                .toList();
    }
}
