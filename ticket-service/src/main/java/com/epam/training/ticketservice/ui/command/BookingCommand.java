package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class BookingCommand {

    private final BookingService bookingService;
    private final UserService userService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final Availabilities availabilities;


    public BookingCommand(BookingService bookingService,
                          UserService userService,
                          MovieService movieService,
                          RoomService roomService,
                          ScreeningService screeningService, Availabilities availabilities) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
        this.availabilities = availabilities;
    }

    @ShellMethodAvailability("isLoggedInAsNotAdmin")
    @ShellMethod(key = "book", value = "Booking seat(s) to a screening")
    public String book(String movieTitle, String roomName, String date, String seats) {
        UserDto user = userService.getLoggedInUser().get();
        ScreeningDto screeningDto = screeningService
                .getScreeningByTitleRoomAndDate(movieTitle, roomName, LocalDateTime
                        .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        String message = bookingService.bookSeats(user, screeningDto, seats);
        return message;

    }

    @ShellMethod(key = "show price for", value = "Show price seat(s) to a screening")
    public String showPrice(String movieTitle, String roomName, String date, String seats) {
        ScreeningDto screeningDto = screeningService
                .getScreeningByTitleRoomAndDate(movieTitle, roomName, LocalDateTime
                        .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return "The price for this booking would be "
                + bookingService.showPrice(screeningDto, seats)
                + " HUF";
    }

    public Availability isLoggedInAsNotAdmin() {
        return availabilities.isLoggedInAsNotAdmin();
    }

}
