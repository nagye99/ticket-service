package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

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
            return movie + " hozzáadva az adatbázishoz.";
        }catch(Exception e){
            return "Sikertelen hozzáadás. Létezik már ilyen film.";
        }
    }

    @ShellMethod(key = "update movie", value = "Update movie's data")
    public String updateMovie(String movieName, String genre, int length) {
        return new MovieDto(movieName, genre, length) + " is updated.";
    }

    @ShellMethod(key = "delete movie", value = "Delete the given movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> movieDto = movieService.deleteMovie(title);
        if(movieDto.isPresent()){
            return movieDto.get() + " törölve.";
        }
        return "Nem létezik ilyen film jelenleg";
    }

    @ShellMethod(key = "list movies", value = "List movies")
    public List<MovieDto> listMovies() {
        return movieService.listMovies();
    }
}