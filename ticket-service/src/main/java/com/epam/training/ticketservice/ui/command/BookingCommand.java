package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@ShellComponent
public class BookingCommand {

    private final BookingService bookingService;
    private final UserService userService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;


    public BookingCommand(BookingService bookingService,
                          UserService userService,
                          MovieService movieService,
                          RoomService roomService,
                          ScreeningService screeningService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
    }

    @ShellMethodAvailability("isLoggedInAsNotAdmin")
    @ShellMethod(key = "book", value = "Booking seat(s) to a screening")
    public String book(String movieTitle, String roomName, String date, String seats) {
        try {
            UserDto user = userService.getLoggedInUser().get();
            Optional<ScreeningDto> screeningDto = screeningService
                    .getScreeningByTitleRoomAndDate(movieTitle, roomName, LocalDateTime
                            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (screeningDto.isPresent()) {
                String message = bookingService.bookSeats(user, screeningDto.get(), seats);
                return message;
            } else {
                return "Screening doesn't exist.";
            }
        } catch (DateTimeParseException e) {
            return "The date format isn't acceptable, it could be in yyyy-mm-ss hh:mm format.";
        } catch (NullPointerException exception) {
            return "Movie or Room doesn't exist.";
        }
    }

    @ShellMethod(key = "show price for", value = "Show price seat(s) to a screening")
    public String showPrice(String movieTitle, String roomName, String date, String seats) {
        try {
            Optional<ScreeningDto> screeningDto = screeningService
                    .getScreeningByTitleRoomAndDate(movieTitle, roomName, LocalDateTime
                            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (screeningDto.isPresent()) {
                return "The price for this booking would be "
                        + bookingService.showPrice(screeningDto.get(), seats)
                        + " HUF";
            } else {
                return "Screening doesn't exist.";
            }
        } catch (DateTimeParseException e) {
            return "The date format isn't acceptable, it could be in yyyy-mm-ss hh:mm format.";
        } catch (NullPointerException exception) {
            return "Movie or Room doesn't exist.";
        }
    }

    private Availability isLoggedInAsNotAdmin() {
        Optional<UserDto> user = userService.getLoggedInUser();
        return user.isPresent() && user.get().getRole() == User.Role.USER
                ? Availability.available()
                : Availability.unavailable("You are not logged in!");
    }
}
