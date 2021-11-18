package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void addMovie(MovieDto movieDto);

    Optional<MovieDto> updateMovie(String title, String genre, Integer length);

    Optional<MovieDto> deleteMovie(String title);

    List<MovieDto> listMovies();

    Optional<MovieDto> getMovieByTitle(String movieName);
}
