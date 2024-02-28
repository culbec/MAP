package controller;

import domain.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import repository.MovieDBRepository;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieController {
    private MovieDBRepository movieDBRepository;

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableColumn<Movie, UUID> idMovie;
    @FXML
    private TableColumn<Movie, String> titleMovie;
    @FXML
    private TableColumn<Movie, String> directorMovie;
    @FXML
    private TableColumn<Movie, Integer> yearMovie;

    ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();

    public void setMovieDBRepository(MovieDBRepository movieDBRepository) {
        this.movieDBRepository = movieDBRepository;
    }

    public void updateModel() {
        this.movieObservableList.setAll(StreamSupport.stream(this.movieDBRepository.getAll().spliterator(), false).collect(Collectors.toList()));
    }

    public void init() {
        this.movieTableView.
    }
}
