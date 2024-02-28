package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.SpecialOffer;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecialOfferDBRepository extends DBRepository<Double, SpecialOffer> {

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from special_offer";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Double aDouble) throws RepositoryException {
        try {
            String sql = "select * from special_offer s where s.sid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, aDouble);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected SpecialOffer extractFromResultSet(ResultSet resultSet) throws SQLException {
        double specialOfferId = resultSet.getDouble("sid");
        double hid = resultSet.getDouble("hid");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        int percents = resultSet.getInt("percents");

        return new SpecialOffer(specialOfferId, hid, startDate, endDate, percents);
    }

    /**
     * Returns the special offers of a hotel based on the hotel ID and the start date.
     * @param hotelId ID of the hotel.
     * @param startDate Start date of the special offer.
     * @return List of special offers of the hotel.
     */
    public List<SpecialOffer> getSpecialOffersOfHotel(double hotelId, Date startDate) {
        try(Connection connection = this.connect()) {
            String sql = "select * from special_offer s where s.hid = ? and s.start_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, hotelId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate.toInstant().toString()));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SpecialOffer> specialOffers = new ArrayList<>();

            while(resultSet.next()) {
                SpecialOffer specialOffer = this.extractFromResultSet(resultSet);
                specialOffers.add(specialOffer);
            }

            return specialOffers;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
