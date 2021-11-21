package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class ScreeningCommand {

    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;

    public ScreeningCommand(MovieService movieService, RoomService roomService, ScreeningService screeningService){
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening", value = "Add screening to database")
    public String createScreening(String movieTitle, String roomName, String date){
        Optional<MovieDto> movie = movieService.getMovieByTitle(movieTitle);
        Optional<RoomDto> room = roomService.getRoomByName(roomName);
        if(movie.isPresent() && room.isPresent()) {
            try{
                ScreeningDto screeningDto = ScreeningDto.builder()
                        .movie(movie.get())
                        .room(room.get())
                        .date(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .build();
            screeningService.addScreening(screeningDto);
            return screeningDto + " is added to database.";
        }catch(DateTimeParseException e){
            return "The date format isn't acceptable, it could be in yyyy-mm-ss hh:mm format.";
        }
        }
        return "Movie or Room doesn't exists.";
    }

    @ShellMethod(key = "delete screening", value = "Delete the given screening")
    public String deleteScreening(String title, String name, String date){
        try {
            Optional<ScreeningDto> screeningDto = screeningService.deleteScreening(title, name, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (screeningDto.isPresent()) {
                return screeningDto.get() + " deleted.";
            }
            return "The screening doesn't exist in the database.";
        }catch (NullPointerException e){
            return "The screening is deleted. But the movie or room was deprecated.";
        }catch(DateTimeParseException exception){
            return "The date format isn't acceptable, it could be in yyyy-mm-ss hh:mm format.";
        }
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreening() {
        try{
            List<ScreeningDto> screeningList = screeningService.listScreenings();
            return screeningList.isEmpty() ? "There are no screenings" : screeningList.stream().map(room -> room.toString()).collect(Collectors.joining("\n"));
        }catch(NullPointerException e){
            return "One of the screening's movie or room doesn't exist.";
        }
    }
}
