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
        if(movie.isPresent() && room.isPresent()){
            ScreeningDto screeningDto = ScreeningDto.builder()
                    .movie(movie.get())
                    .room(room.get())
                    .date(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .build();
            screeningService.addScreening(screeningDto);
            return screeningDto + " is added to database.";
        }
        return "Movie or Room doesn't exists.";
    }

    @ShellMethod(key = "delete screening", value = "Delete the given screening")
    public String deleteScreening(String title, String name, String date){
        Optional<ScreeningDto> screeningDto = screeningService.deleteScreening(title, name, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        if(screeningDto.isPresent()){
            return screeningDto.get() + " deleted.";
        }
        return "The screening doesn't exist in the database.";
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreening() {
        List<ScreeningDto> screeningList = screeningService.listScreenings();
        return screeningList.isEmpty() ? "There are no screenings" : screeningList.stream().map(room -> room.toString()).collect(Collectors.joining("\n"));
    }
}
