package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.model.Movie;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommand {

    @ShellMethod(key = "create movie", value ="Add movie to database")
    public Movie createMovie(String movieName, String genre, int length) {

        return new Movie(movieName, genre, length);
    }

    @ShellMethod(key = "update movie", value = "Update movie's data")
    public String updateMovie(String movieName, String genre, int length) {
        return new Movie(movieName, genre, length) + " is updated.";
    }

    @ShellMethod(key = "delete movie", value = "Delete the given movie")
    public String deleteMovie(String movieName) {
        return movieName + " is deleted.";
    }

    @ShellMethod(key = "list movies", value = "List movies")
    public String listMovies() {
        return "Movies list";
    }
}
