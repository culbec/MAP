package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Hotel;
import ro.ubbcluj.cs.map.template.Domain.SpecialOffer;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Utilities.HotelType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HotelDBRepository extends DBRepository<Double, Hotel> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from hotel";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Double aDouble) throws RepositoryException {
        try {
            String sql = "select * from hotel h where h.hid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, aDouble);

            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Hotel extractFromResultSet(ResultSet resultSet) throws SQLException {
        double hotelId = resultSet.getDouble("hid");
        double locationId = resultSet.getDouble("lid");
        String hotelName = resultSet.getString("name");
        int noRooms = resultSet.getInt("no_rooms");
        double pricePerNight = resultSet.getDouble("price_per_night");
        HotelType type = HotelType.valueOf(resultSet.getString("type"));

        return new Hotel(hotelId, locationId, hotelName, noRooms, pricePerNight, type);
    }
}
