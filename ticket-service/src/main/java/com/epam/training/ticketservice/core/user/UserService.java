package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> signIn(String username, String password);

    Optional<UserDto> signOut();

    Optional<UserDto> getLoggedInUser();

    void signUp(String username, String password);
}
