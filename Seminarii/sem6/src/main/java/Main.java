import domain.Movie;
import repository.MovieDBRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MovieDBRepository movieDBRepository = new MovieDBRepository();

        Set<Movie> movieSet = (Set<Movie>) movieDBRepository.getAll();
        if(movieSet != null) {
            movieSet.forEach(System.out::println);
        }

        Optional<Movie> retGet = movieDBRepository.getOne(UUID.fromString("71dd4b3a-06d7-4dff-990a-0df9ae35ab58"));
        retGet.ifPresent(System.out::println);

        Movie movie = new Movie("Spider-Man 2", "Spielberg", 2002);
        Optional<Movie> retSave = movieDBRepository.save(movie);
        retSave.ifPresent(System.out::println);

        Movie movieSaved = new Movie("Barbie", "Kiesha", 2023);
        Optional<Movie> retEmpty = movieDBRepository.save(movieSaved);
        System.out.println(retEmpty);
    }
}
