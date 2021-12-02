package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;
    private final Availabilities availabilities;

    public MovieCommand(MovieService movieService, Availabilities availabilities) {
        this.movieService = movieService;
        this.availabilities = availabilities;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create movie", value = "Add movie to database")
    public String createMovie(String title, String genre, int length) {
        MovieDto movie = MovieDto.builder().title(title).genre(genre).length(length).build();
        return movieService.addMovie(movie);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update movie", value = "Update movie's data")
    public String updateMovie(String title, String genre, int length) {
        MovieDto movieDto = MovieDto.builder().title(title).genre(genre).length(length).build();
        return movieService.updateMovie(movieDto);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete movie", value = "Delete the given movie")
    public String deleteMovie(String title) {
        movieService.deleteMovie(title);
        return "Now " + title + " is not in database.";
    }

    @ShellMethod(key = "list movies", value = "List movies")
    public String listMovies() {
        List<MovieDto> movieList = movieService.listMovies();
        return movieList.isEmpty() ? "There are no movies at the moment" : movieList.stream()
                .map(movie -> movie.toString()).collect(Collectors.joining("\n"));
    }

    private Availability isAdmin() {
        return availabilities.isAdmin();
    }
}