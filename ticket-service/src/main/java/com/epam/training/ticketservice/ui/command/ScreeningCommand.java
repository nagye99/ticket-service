package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ShellComponent
public class ScreeningCommand {

    private final MovieService movieService;
    private final RoomService roomService;

    public ScreeningCommand(MovieService movieService, RoomService roomService){
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @ShellMethod(key = "create screening", value = "Add screening to database")
    public ScreeningDto createScreening(String movieName, String roomName, String date) throws ParseException {
        return new ScreeningDto(movieService.getMovieByTitle(movieName).get(), roomService.getRoomByName(roomName).get(), new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date));
    }

    @ShellMethod(key = "delete screening", value = "Delete the given screening")
    public String deleteScreening(String movieName, String roomName, String date) {
        return "delete screening";
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreening() {
        return "Screenings list";
    }
}
