package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class UserCommand {

    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "describe account", value = "Describe account")
    public String describeAccount() {
        Optional<UserDto> userDto = userService.getLoggedInUser();
        if (userDto.isEmpty()) {
            return "You are not signed in";
        } else if (userDto.get().getRole() == User.Role.ADMIN) {
            return "Signed in with privileged account " + userDto.get().getUsername();
        }
        return "Signed in with account " + userDto.get().getUsername();
    }

    @ShellMethod(key = "sign up", value = "Sign up new account")
    public String signUp(String userName, String password) {
        try {
            userService.signUp(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }

    @ShellMethod(key = "sign in", value = "Sign in")
    public String signIn(String username, String password) {
        Optional<UserDto> user = userService.signIn(username, password);
        if (user.isEmpty() || user.get().getRole() == User.Role.ADMIN) {
            return "Login failed due to incorrect credentials";
        }
        return user.get() + " is logged in!";
    }


    @ShellMethod(key = "sign in privileged", value = "Sign in with privileged account")
    public String signInPrivileged(String username, String password) {
        Optional<UserDto> user = userService.signIn(username, password);
        if (user.isEmpty() || user.get().getRole() == User.Role.USER) {
            return "Login failed due to incorrect credentials";
        }
        return user.get() + " is logged in!";
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        Optional<UserDto> user = userService.signOut();
        if (user.isEmpty()) {
            return "You need to login first!";
        }
        return user.get() + " is logged out!";
    }
}