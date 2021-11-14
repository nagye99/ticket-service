package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieServiceImpl implements MovieService{

    private List<Movie> movieList;

    @Override
    public Optional<Movie> getMovieByTitle(String movieName) {
        return movieList.stream()
                .filter(movie -> movie.getTitle().equals(movieName))
                .findFirst();
    }
}
