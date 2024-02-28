package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Reservation;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class ReservationDBRepository extends DBRepository<Double, Reservation> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from reservation";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Double aDouble) throws RepositoryException {
        try {
            String sql = "select * from reservation r where r.rid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, aDouble);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Reservation extractFromResultSet(ResultSet resultSet) throws SQLException {
        double reservationId = resultSet.getDouble("rid");
        Long clientId = resultSet.getLong("cid");
        double hotelId = resultSet.getDouble("hid");
        LocalDateTime startDate = resultSet.getTimestamp("start_date").toLocalDateTime();
        int noNights = resultSet.getInt("no_nights");

        return new Reservation(reservationId, clientId, hotelId, startDate, noNights);
    }

    public Optional<Reservation> save(Reservation reservation) throws RepositoryException {
        try (Connection connection = this.connect()) {
            Optional<Reservation> optionalReservation = this.getOne(reservation.getReservationId());
            if (optionalReservation.isPresent()) {
                return optionalReservation;
            }

            String sql = "insert into reservation(rid, cid, hid, start_date, no_nights) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, reservation.getReservationId());
            preparedStatement.setLong(2, reservation.getClientId());
            preparedStatement.setDouble(3, reservation.getHotelId());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(reservation.getStartDate()));
            preparedStatement.setInt(5, reservation.getNoNights());

            preparedStatement.execute();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public double getValidId() {
        try(Connection connection = this.connect()) {
            String sql = "select max(rid) from reservation";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getDouble(1) + 1;
            }
            return 1;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
