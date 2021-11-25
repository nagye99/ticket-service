package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void addMovie(MovieDto movieDto);

    void updateMovie(MovieDto movieDto);

    Optional<MovieDto> deleteMovie(String title);

    List<MovieDto> listMovies();

    Optional<MovieDto> getMovieByTitle(String title);

    Integer getMovieId(String title);

    long getMinutes(String title);
}
