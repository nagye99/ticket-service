package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
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
        Objects.requireNonNull(movieDto, "Movie cannot be null");
        Objects.requireNonNull(movieDto.getTitle(), "Title of movie cannot be null during saving!");
        Objects.requireNonNull(movieDto.getGenre(), "Genre of movie cannot be null during saving!");
        Objects.requireNonNull(movieDto.getLength(), "Duration of movie cannot be null during saving!");
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public String updateMovie(MovieDto movieDto) {
        Objects.requireNonNull(movieDto, "Movie cannot be null");
        Objects.requireNonNull(movieDto.getTitle(), "Title of movie cannot be null during update!");
        Objects.requireNonNull(movieDto.getGenre(), "Genre of movie cannot be null during update!");
        Objects.requireNonNull(movieDto.getLength(), "Duration of movie cannot be null during update!");
        Optional<Movie> movieOpt = movieRepository.findByTitle(movieDto.getTitle());
        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();
            movie.setGenre(movieDto.getGenre());
            movie.setLength(movieDto.getLength());
            movieRepository.save(movie);
            return movieDto + " is updated.";
        } else {
            return "The movie doesn't exist.";
        }
    }

    @Override
    public void deleteMovie(String title) {
        Objects.requireNonNull(title, "Title of movie cannot be null during delete!");
        movieRepository.deleteByTitle(title);
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream().map(this::convertMovieEntityToMovieDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieByTitle(String movieName) {
        Optional<MovieDto> movieDto = convertMovieEntityToMovieDto(movieRepository.findByTitle(movieName));
        if (movieDto.isPresent()) {
            return movieDto.get();
        } else {
            throw new IllegalArgumentException("The movie doesn't exist");
        }
    }

    @Override
    public Integer getMovieId(String title) {
        Optional<Movie> movie =
                movieRepository.findByTitle(title);
        if (movie.isPresent()) {
            return movie.get().getId();
        }
        throw new NullPointerException("The movie doesn't exist.");
    }

    @Override
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

    private Optional<MovieDto> convertMovieEntityToMovieDto(Optional<Movie> movie) {
        return movie.isEmpty() ? Optional.empty() : Optional.of(convertMovieEntityToMovieDto(movie.get()));
    }
}
