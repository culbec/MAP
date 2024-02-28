package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Utilities.Hobby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDBRepository extends DBRepository<Long, Client> {
    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        try {
            String sql = "select * from client";
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Long aLong) throws RepositoryException {
        try {
            String sql = "select * from client c where c.cid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Client extractFromResultSet(ResultSet resultSet) throws SQLException {
        long clientId = resultSet.getLong("cid");
        String name = resultSet.getString("name");
        int fidelityGrade = resultSet.getInt("fidelity_grade");
        int age = resultSet.getInt("age");
        Hobby hobby = Hobby.valueOf(resultSet.getString("hobby"));

        return new Client(clientId, name, fidelityGrade, age, hobby);
    }
}
