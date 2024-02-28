package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Order;
import ro.ubbcluj.cs.map.template.Domain.OrderItem;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Utilities.OrderStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepository extends DBRepository<Integer, Order>{
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from public.\"order\"";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Integer integer) throws RepositoryException {
        String sql = "select * from public.\"order\" where public.\"order\".oid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, integer);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, Order entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, Order entity) throws RepositoryException {
        String sql = "insert into public.\"order\" values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getTable());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setString(4, String.valueOf(entity.getOrderStatus()));

            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, Order entity) throws RepositoryException {
        String sql = "update public.\"order\" set order_status = ? where oid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getOrderStatus().toString());
            preparedStatement.setInt(2, entity.getId());
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        Integer oid = resultSet.getInt(1);
        Integer tid = resultSet.getInt(2);
        LocalDateTime date = resultSet.getTimestamp(3).toLocalDateTime();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(4));

        List<OrderItem> orderItems = new ArrayList<>();

        try(Connection connection = this.connect()) {
            String sql  = "select order_item.oid, mid, quantity from order_item inner join public.\"order\" on public.\"order\".oid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, oid);
            ResultSet resultSetMenuItem = preparedStatement.executeQuery();

            while (resultSetMenuItem.next()) {
                orderItems.add(new OrderItem(resultSetMenuItem.getInt(1), resultSetMenuItem.getInt(2), resultSetMenuItem.getInt(3)));
            }

            return new Order(oid, tid, orderItems, date, orderStatus);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Integer getValidId() {
        try(Connection connection = this.connect()) {
            String sql = "select max(oid) from public.\"order\"";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
            return 1;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        try(Connection connection = this.connect()) {
            orderItems.forEach(orderItem -> {
                String sql = "insert into order_item values (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, orderItem.getFirst());
                    preparedStatement.setInt(2, orderItem.getSecond());
                    preparedStatement.setInt(3, orderItem.getMenuItemQuantity());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RepositoryException(e.getMessage());
                }

            });
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
