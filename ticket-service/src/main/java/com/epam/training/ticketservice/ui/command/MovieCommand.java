package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;
    private final UserService userService;

    public MovieCommand(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create movie", value = "Add movie to database")
    public String createMovie(String title, String genre, int length) {
        try {
            MovieDto movie = MovieDto.builder().title(title).genre(genre).length(length).build();
            movieService.addMovie(movie);
            return movie + " added to database.";
        } catch (Exception e) {
            return "Unsuccessful creating. The movie is already in the database.";
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update movie", value = "Update movie's data")
    public String updateMovie(String title, String genre, int length) {
        try {
            MovieDto movieDto = MovieDto.builder().title(title).genre(genre).length(length).build();
            movieService.updateMovie(movieDto);
            return movieDto + " is updated.";
        } catch (IllegalArgumentException e) {
            return "The movie doesn't exist.";
        }

    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete movie", value = "Delete the given movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> movieDto = movieService.deleteMovie(title);
        if (movieDto.isPresent()) {
            return movieDto.get() + " deleted.";
        }
        return "The movie doesn't exist in the database.";
    }

    @ShellMethod(key = "list movies", value = "List movies")
    public String listMovies() {
        List<MovieDto> movieList = movieService.listMovies();
        return movieList.isEmpty() ? "There are no movies at the moment" : movieList.stream()
                .map(movie -> movie.toString()).collect(Collectors.joining("\n"));
    }

    private Availability isAdmin() {
        Optional<UserDto> user = userService.getLoggedInUser();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not signed in as admin!");
    }
}