package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.Optional;

public interface MovieService {
    Optional<Movie> getMovieByTitle(String movieName);
}
