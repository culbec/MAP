package repository;

import domain.Movie;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class MovieDBRepository implements Repository<UUID, Movie> {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/seminar6";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    /**
     * Connects to a SQL Database.
     *
     * @return Connection to a SQL Database.
     */
    private Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    /**
     * Extracts a movie from the fields of a result set.
     * @param resultSet Result set of a executed query.
     * @return Movie encapsulated in resultSet.
     */
    private Movie extractMovie(ResultSet resultSet) throws SQLException {
        String ID = resultSet.getString("ID");
        String title = resultSet.getString("title");
        String director = resultSet.getString("director");
        int year = resultSet.getInt("year");

        return new Movie(UUID.fromString(ID), title, director, year);
    }

    /**
     * Checks if the repository is empty.
     *
     * @return true if the repository is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() throws SQLException {
        return false;
    }

    /**
     * Size of the repository.
     *
     * @return Number of entities E stored in the repository.
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * All the contents of the repository.
     *
     * @return All the values stored in the repository.
     */
    @Override
    public Iterable<Movie> getAll() {
        try (Connection connection = this.connect()) {
            String sql = "select * from \"Movies\"";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            Set<Movie> movieSet = new HashSet<>();

            while (resultSet.next()) {
                movieSet.add(this.extractMovie(resultSet));
            }

            return movieSet;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    /**
     * Searches for one entity in the repository.
     *
     * @param uuid ID of the Entity to search
     * @return {@code Optional}
     * - null if the entity with the specified ID does not exist
     * - otherwise returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    @Override
    public Optional<Movie> getOne(UUID uuid) throws IllegalArgumentException {
        try (Connection connection = this.connect()) {
            String sql = "select * from public.\"Movies\" where public.\"Movies\".\"ID\" = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, uuid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(this.extractMovie(resultSet));
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Adds an entity to the repository.
     *
     * @param movie Entity that should be added
     * @return an {@code Optional}
     * - null if the entity was saved
     * - the entity if it was already saved
     * @throws IllegalArgumentException If the entity is null.
     */
    @Override
    public Optional<Movie> save(Movie movie) throws IllegalArgumentException {
        try(Connection connection = this.connect()) {
            String sql_id = "select * from \"Movies\" where \"ID\" = ?";
            PreparedStatement statement_id = connection.prepareStatement(sql_id);

            statement_id.setObject(1, movie.getId());
            ResultSet resultSet_id = statement_id.executeQuery();
            if(resultSet_id.next()) {
                return Optional.of(this.extractMovie(resultSet_id));
            }

            String sql_movie = "select * from \"Movies\" where \"title\" = ? AND \"director\" = ? AND \"year\" = ?";
            PreparedStatement statement_movie = connection.prepareStatement(sql_movie);

            statement_movie.setString(1, movie.getTitle());
            statement_movie.setString(2, movie.getDirector());
            statement_movie.setInt(3, movie.getYear());

            ResultSet resultSet_movie = statement_movie.executeQuery();
            if(resultSet_movie.next()) {
                System.out.println("The same movie already exists!");
                return Optional.of(this.extractMovie(resultSet_movie));
            }

            String sql_insert = "insert into \"Movies\"(\"ID\", title, director, year) values (?, ?, ?, ?)";
            PreparedStatement statement_insert = connection.prepareStatement(sql_insert);

            statement_insert.setObject(1, movie.getId());
            statement_insert.setString(2, movie.getTitle());
            statement_insert.setString(3, movie.getDirector());
            statement_insert.setInt(4, movie.getYear());

            statement_insert.execute(); // inserted value into the query

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Removes an entity from the repository
     *
     * @param uuid ID of the entity to remove.
     * @return an {@code Optional}
     * - null if there is no entity with the given id
     * - otherwise returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    @Override
    public Optional<Movie> delete(UUID uuid) throws IllegalArgumentException {
        return Optional.empty();
    }

    /**
     * Updates an entity.
     *
     * @param movie New entity.
     * @return an {@code Optional}
     * - null if the entity was updated
     * - otherwise (e.g. id does not exist) returns the entity
     * @throws IllegalArgumentException If the id is null.
     */
    @Override
    public Optional<Movie> update(Movie movie) throws IllegalArgumentException {
        return Optional.empty();
    }
}
