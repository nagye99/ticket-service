package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;

    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(key = "create movie", value ="Add movie to database")
    public String createMovie(String title, String genre, int length) {
        try{
            MovieDto movie = MovieDto.builder().title(title).genre(genre).length(length).build();
            movieService.addMovie(movie);
            return movie + " added to database.";
        }catch(Exception e){
            return "Unsuccessful creating. The movie is already in the database.";
        }
    }

    @ShellMethod(key = "update movie", value = "Update movie's data")
    public String updateMovie(String title, String genre, int length) {
        return MovieDto.builder().title(title).genre(genre).length(length).build() + " is updated.";
    }

    @ShellMethod(key = "delete movie", value = "Delete the given movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> movieDto = movieService.deleteMovie(title);
        if(movieDto.isPresent()){
            return movieDto.get() + " deleted.";
        }
        return "The movie doesn't exist in the database.";
    }

    @ShellMethod(key = "list movies", value = "List movies")
    public String listMovies() {
        List<MovieDto> movieList = movieService.listMovies();
        return movieList.isEmpty() ? "There are no movies at the moment" : movieList.stream().map(movie -> movie.toString()).collect(Collectors.joining("\n"));
    }
}