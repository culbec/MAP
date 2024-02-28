package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Domain.Request;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestDBRepository extends DBRepository<Long, Request> {
    @Override
    protected PreparedStatement statementCount(Connection connection) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementSelectAll(Connection connection) throws RepositoryException {
        String sql = "select * from request";
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnID(Connection connection, Long aLong) throws RepositoryException {
        String sql = "select * from request where request.cid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementSelectOnFields(Connection connection, Request entity) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementInsert(Connection connection, Request entity) throws RepositoryException {
        String sql = "insert into request(did, pid, date) values(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getDriver().getId());
            preparedStatement.setLong(2, entity.getPerson().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            return preparedStatement;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected PreparedStatement statementDelete(Connection connection, Long aLong) throws RepositoryException {
        return null;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection connection, Request entity) throws RepositoryException {
        return null;
    }

    @Override
    protected Request extractFromResultSet(ResultSet resultSet) throws SQLException {
        Long cid = resultSet.getLong("cid");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();

        Long pid = resultSet.getLong("pid");
        Long did = resultSet.getLong("did");

        try (Connection connection = this.connect()) {
            String sqlPerson = "select * from person p where p.pid = ?";
            PreparedStatement preparedStatementPerson = connection.prepareStatement(sqlPerson);
            preparedStatementPerson.setLong(1, pid);

            ResultSet resultSetPerson = preparedStatementPerson.executeQuery();
            resultSetPerson.next();
            String usernamePerson = resultSetPerson.getString("username");
            String namePerson = resultSetPerson.getString("name");
            Person person = new Person(pid, usernamePerson, namePerson);

            String sqlDriver = "select * from driver d inner join public.person p on p.pid = d.did where p.pid = ?";
            PreparedStatement preparedStatementDriver = connection.prepareStatement(sqlDriver);
            preparedStatementDriver.setLong(1, did);

            ResultSet resultSetDriver = preparedStatementDriver.executeQuery();
            resultSetDriver.next();
            String usernameDriver = resultSetDriver.getString("username");
            String nameDriver = resultSetDriver.getString("name");
            String indicativMasina = resultSetDriver.getString("indicativ-masina");
            Driver driver = new Driver(did, usernameDriver, nameDriver, indicativMasina);

            return new Request(cid, person, driver, date);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Iterable<Request> getRequestsOfDriver(Long driverId) {
        try(Connection connection = this.connect()) {
            String sql = "select * from request r where r.did = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, driverId);

            ResultSet resultSet = preparedStatement.executeQuery();
            connection.close();

            List<Request> requests = new ArrayList<>();
            while(resultSet.next()) {
                requests.add(this.extractFromResultSet(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Iterable<Person> getClientsFromPage(Iterable<Request> requests, Pageable pageable) {
        return StreamSupport.stream(requests.spliterator(), false)
                .map(Request::getPerson)
                .distinct()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    public Double getMeanOfTheLast3Months(Long driverId) {
        try(Connection connection = this.connect()) {
            String sql = "select * from request r where r.did = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();

            int requestCount = 0;
            int currentMonth = LocalDateTime.now().getMonth().getValue();
            int prevMonth = 0;
            int prevPrevMonth = 0;

            if (currentMonth == 1) {
                prevMonth = 12;
                prevPrevMonth = 11;
            } else if (currentMonth == 2) {
                prevMonth = 1;
                prevPrevMonth = 12;
            } else {
                prevMonth = currentMonth - 1;
                prevPrevMonth = prevMonth - 1;
            }

            while(resultSet.next()) {
                Request request = this.extractFromResultSet(resultSet);



                if (request.getDate().getMonth().getValue() == currentMonth ||
                    request.getDate().getMonth().getValue() == prevMonth ||
                    request.getDate().getMonth().getValue() == prevPrevMonth) {
                    requestCount = requestCount + 1;
                }

            }
            return (double) requestCount / 180;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
