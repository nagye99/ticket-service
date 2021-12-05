package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;

public interface MovieService {

    void addMovie(MovieDto movieDto);

    String updateMovie(MovieDto movieDto);

    void deleteMovie(String title);

    List<MovieDto> listMovies();

    MovieDto getMovieByTitle(String title);

    Integer getMovieId(String title);

    long getMinutes(String title);
}
