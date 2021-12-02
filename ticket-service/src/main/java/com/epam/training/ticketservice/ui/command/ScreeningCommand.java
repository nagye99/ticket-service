package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ScreeningCommand {

    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final Availabilities availabilities;

    public ScreeningCommand(MovieService movieService,
                            RoomService roomService,
                            ScreeningService screeningService, Availabilities availabilities) {
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
        this.availabilities = availabilities;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create screening", value = "Add screening to database")
    public String createScreening(String movieTitle, String roomName, String date) {
        MovieDto movie = movieService.getMovieByTitle(movieTitle);
        RoomDto room = roomService.getRoomByName(roomName);
        try {
            ScreeningDto screeningDto = ScreeningDto.builder()
                    .movie(movie)
                    .room(room)
                    .date(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .build();
            screeningService.addScreening(screeningDto);
            return screeningDto + " is added to database.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete screening", value = "Delete the given screening")
    public String deleteScreening(String title, String name, String date) {
        screeningService.deleteScreening(title,
                name,
                LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return "The screening doesn't exist anymore in the database.";
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreening() {
        try {
            List<ScreeningDto> screeningList = screeningService.listScreenings();
            return screeningList.isEmpty()
                    ? "There are no screenings"
                    : screeningList.stream().map(room -> room.toString()).collect(Collectors.joining("\n"));
        } catch (NullPointerException e) {
            return "One of the screening's movie or room doesn't exist.";
        }
    }

    private Availability isAdmin() {
        return availabilities.isAdmin();
    }
}
