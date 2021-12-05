package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class UserCommand {

    private final UserService userService;
    private final BookingService bookingService;

    public UserCommand(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @ShellMethod(key = "describe account", value = "Describe account")
    public String describeAccount() {
        Optional<UserDto> userDto = userService.getLoggedInUser();
        if (userDto.isEmpty()) {
            return "You are not signed in";
        } else if (userDto.get().getRole() == User.Role.ADMIN) {
            return "Signed in with privileged account '" + userDto.get().getUsername() + "'";
        }
        String message = "Signed in with account '" + userDto.get().getUsername() + "'\n";
        List<BookingDto> bookings = bookingService.getBookingByUser(userDto.get().getUsername());
        if (bookings.size() == 0) {
            message += "You have not booked any tickets yet";
        } else {
            message += "Your previous bookings are\n";
            message += bookings.stream().map(booking -> booking.toString()).collect(Collectors.joining("\n"));
        }
        return message;
    }

    @ShellMethod(key = "sign up", value = "Sign up new account")
    public String signUp(String userName, String password) {
        try {
            return userService.signUp(userName, password);
        }  catch (DataIntegrityViolationException e) {
            return "Registration failed!";
        }
    }

    @ShellMethod(key = "sign in", value = "Sign in")
    public String signIn(String username, String password) {
        Optional<UserDto> user = userService.signIn(username, password);
        if (user.isEmpty() || user.get().getRole() == User.Role.ADMIN) {
            return "Login failed due to incorrect credentials";
        }
        return user.get().getUsername() + " is logged in!";
    }


    @ShellMethod(key = "sign in privileged", value = "Sign in with privileged account")
    public String signInPrivileged(String username, String password) {
        Optional<UserDto> user = userService.signIn(username, password);
        if (user.isEmpty() || user.get().getRole() == User.Role.USER) {
            return "Login failed due to incorrect credentials";
        }
        return user.get().getUsername() + " is logged in!";
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        userService.signOut();
        return " Logged out!";
    }
}