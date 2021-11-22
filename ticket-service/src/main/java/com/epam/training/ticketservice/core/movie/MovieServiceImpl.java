package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(MovieDto movieDto) {
        Objects.requireNonNull(movieDto.getTitle(), "Title of movie cannot be null during saving!");
        Objects.requireNonNull(movieDto.getGenre(), "Genre of movie cannot be null during saving!");
        Objects.requireNonNull(movieDto.getLength(), "Duration of movie cannot be null during saving!");
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public Optional<MovieDto> updateMovie(String title, String genre, Integer length) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        Objects.requireNonNull(title, "Title of movie cannot be null during delete!");
        List<Movie> deletedMovies = movieRepository.deleteByTitle(title);
        Optional<Movie> movie = deletedMovies.isEmpty() ? Optional.empty() : Optional.of(deletedMovies.get(0));
        return convertMovieEntityToMovieDto(movie);
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream().map(this::convertMovieEntityToMovieDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDto> getMovieByTitle(String movieName) {
        return convertMovieEntityToMovieDto(movieRepository.findByTitle(movieName));
    }

    public long getMinutes(String title) {
        Optional<Movie> movieOptional = movieRepository.findByTitle(title);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            return movie.getLength();
        }
        throw new NullPointerException();
    }


    private MovieDto convertMovieEntityToMovieDto(Movie movie) {
        return MovieDto.builder().title(movie.getTitle()).genre(movie.getGenre()).length(movie.getLength()).build();
    }

    private  Optional<MovieDto> convertMovieEntityToMovieDto(Optional<Movie> movie) {
        return movie.isEmpty() ? Optional.empty() : Optional.of(convertMovieEntityToMovieDto(movie.get()));
    }
}
