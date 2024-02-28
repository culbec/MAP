package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Entity;
import ro.ubbcluj.cs.map.template.Domain.MenuItem;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemDBRepository extends DBRepository<Integer, MenuItem> {
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from menu";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Integer integer) throws RepositoryException {
        String sql = "select * from menu where menu.mid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, MenuItem entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, MenuItem entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, MenuItem entity) throws RepositoryException {
        return null;
    }

    @Override
    protected MenuItem extractFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(1);
        String category = resultSet.getString(2);
        String item = resultSet.getString(3);
        Double price = resultSet.getDouble(4);
        String currency = resultSet.getString(5);

        return new MenuItem(id, category, item, price, currency);
    }
}
