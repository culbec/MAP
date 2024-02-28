package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Ticket;
import ro.ubbcluj.cs.map.template.Domain.Triple;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDBRepository extends DBRepository<Triple<String, String, LocalDate>, Ticket>{
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from tickets";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Triple<String, String, LocalDate> stringStringLocalDateTriple) throws RepositoryException {
        try {
            String sql = "select * from tickets t where t.trainId = ? and t.departureCityId = ? and t.date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stringStringLocalDateTriple.getFirst());
            preparedStatement.setString(2, stringStringLocalDateTriple.getSecond());
            preparedStatement.setDate(3, java.sql.Date.valueOf(stringStringLocalDateTriple.getThird()));

            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Ticket extractFromResultSet(ResultSet resultSet) throws SQLException {
        String trainId = resultSet.getString("trainId");
        String departureCityId = resultSet.getString("departureCityId");
        LocalDate date = resultSet.getDate("date").toLocalDate();

        return new Ticket(trainId, departureCityId, date);
    }

    /**
     * Saves a ticket to the database.
     * @param ticket Ticket to be saved.
     * @throws RepositoryException If a problem was encountered during the operation.
     */
    public void save(Ticket ticket) throws RepositoryException {
        try (Connection connection = this.connect()) {
            String sql = "insert into tickets values (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, ticket.getTrainId());
                statement.setString(2, ticket.getDepartureCityId());
                statement.setDate(3, java.sql.Date.valueOf(ticket.getDate()));
                statement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    /**
     * Returns all the tickets on a given date from a given city.
     * @param departureCityId City from which the tickets depart.
     * @param date Date on which the tickets depart.
     * @return List of tickets on the given date from the given city.
     * @throws RepositoryException If a problem was encountered during the operation.
     */
    public List<Ticket> getTicketsOnDate(String departureCityId, LocalDate date) throws RepositoryException {
        try (Connection connection = this.connect()) {
            String sql = "select * from tickets t where t.departureCityId = ? and t.date = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, departureCityId);
                statement.setDate(2, java.sql.Date.valueOf(date));
                ResultSet resultSet = statement.executeQuery();

                List<Ticket> tickets = new ArrayList<>();
                while (resultSet.next()) {
                    tickets.add(this.extractFromResultSet(resultSet));
                }

                return tickets;
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    /**
     * Returns the most sold ticket.
     * @return Tuple containing the train ID and the date of the most sold ticket.
     * @throws RepositoryException If a problem was encountered during the operation.
     */
    public Tuple<String, LocalDate> getMostSoldTicket() throws RepositoryException {
        try (Connection connection = this.connect()) {
            String sql = "select t.trainId, t.date from tickets t group by t.trainId, t.date order by count(*) desc limit 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String trainId = resultSet.getString("trainId");
                    LocalDate date = resultSet.getDate("date").toLocalDate();

                    return new Tuple<>(trainId, date);
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }

        return null;
    }
}
