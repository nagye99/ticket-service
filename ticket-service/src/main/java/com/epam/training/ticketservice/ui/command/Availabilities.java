package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Availabilities {

    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final UserService userService;

    public Availabilities(MovieService movieService,
                            RoomService roomService,
                            ScreeningService screeningService,
                            UserService userService) {
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningService = screeningService;
        this.userService = userService;
    }

    public Availability isAdmin() {
        Optional<UserDto> user = userService.getLoggedInUser();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not signed in as admin!");
    }

    public Availability isLoggedInAsNotAdmin() {
        Optional<UserDto> user = userService.getLoggedInUser();
        return user.isPresent() && user.get().getRole() == User.Role.USER
                ? Availability.available()
                : Availability.unavailable("You are not logged in!");
    }
}
