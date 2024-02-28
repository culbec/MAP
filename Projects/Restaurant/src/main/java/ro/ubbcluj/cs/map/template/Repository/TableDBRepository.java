package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Table;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDBRepository extends DBRepository<Integer, Table> {
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from public.\"table\"";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Integer integer) throws RepositoryException {
        String sql = "select * from public.\"table\" where public.\"table\".tid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, Table entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, Table entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, Table entity) throws RepositoryException {
        return null;
    }

    @Override
    protected Table extractFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(1);
        return new Table(id);
    }
}
