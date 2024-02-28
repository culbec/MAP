package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Flight;
import ro.ubbcluj.cs.map.template.Domain.Ticket;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;

public class TicketDBRepository extends DBRepository<Tuple<String, Long>, Ticket>{
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from ticket";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Tuple<String, Long> tuple) throws RepositoryException {
        String sql = "select * from ticket t where t.username = ? and t.fid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tuple.getFirst());
            preparedStatement.setLong(2, tuple.getSecond());
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Ticket extractFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("username");
        Long fid = resultSet.getLong("fid");
        LocalDateTime purchaseTime = resultSet.getTimestamp("purchase-time").toLocalDateTime();

        return new Ticket(username, fid, purchaseTime);
    }

    public int getNumberOfTicketsOfFlight(Long flightId) {
        try(Connection connection = this.connect()) {
            String sql = "select count(*) from ticket t where t.fid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, flightId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int numberOfTickets = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return numberOfTickets;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void saveTicket(Ticket ticket) throws RepositoryException {
        try(Connection connection = this.connect()) {
            /*PreparedStatement preparedStatementFind = this.statementSelectOnID(connection, new Tuple<>(ticket.getUsername(), ticket.getFlightId()));
            ResultSet resultSetFind = preparedStatementFind.executeQuery();

            if (resultSetFind.next()) {
                throw new RepositoryException("This client already bought a ticket to this flight!");
            }*/

            String sqlInsert = "insert into ticket(username, fid, \"purchase-time\") values(?, ?, ?)";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsert);
            preparedStatementInsert.setString(1, ticket.getUsername());
            preparedStatementInsert.setLong(2, ticket.getFlightId());
            preparedStatementInsert.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseTime()));

            preparedStatementInsert.execute();

            preparedStatementInsert.close();
            /*resultSetFind.close();
            preparedStatementFind.close();*/
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Flight getFlightOfTicket(Long flightId) {
        try(Connection connection = this.connect()) {
            String sql = "select * from flight f where f.fid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, flightId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Flight flight = null;

            if (resultSet.next()) {
                Long id = resultSet.getLong("fid");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                LocalDateTime departureTime = resultSet.getTimestamp("departure-time").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landing-time").toLocalDateTime();
                int seats = resultSet.getInt("seats");

                flight = new Flight(id, from, to, departureTime, landingTime, seats);
            }
            return flight;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
