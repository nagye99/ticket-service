package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private UserService underTest = new UserServiceImpl(userRepository);

    @Test
    public void testSignInShouldThrowNullPointerExceptionWhenUsernameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signIn(null, "pass"));
    }

    @Test
    public void testSignInShouldThrowNullPointerExceptionWhenPasswordIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signIn("user", null));
    }

    @Test
    public void testSignInShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        User user = new User("user", "password", User.Role.USER);
        Optional<User> expected = Optional.of(user);
        when(userRepository.findByUsernameAndPassword("user", "pass")).thenReturn(Optional.of(user));

        // When
        Optional<UserDto> actual = underTest.signIn("user", "pass");

        // Then
        assertEquals(expected.get().getUsername(), actual.get().getUsername());
        assertEquals(expected.get().getRole(), actual.get().getRole());
        verify(userRepository).findByUsernameAndPassword("user", "pass");
    }

    @Test
    public void testSignInShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDto> expected = Optional.empty();
        when(userRepository.findByUsernameAndPassword("dummy", "dummy")).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.signIn("dummy", "dummy");

        // Then
        assertEquals(expected, actual);
        verify(userRepository).findByUsernameAndPassword("dummy", "dummy");
    }

    @Test
    public void testSignOutShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.signOut();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignOutShouldReturnThePreviouslyLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        UserDto user = new UserDto("user", User.Role.USER);
        Optional<UserDto> expected = Optional.of(user);
        underTest = new UserServiceImpl(user, userRepository);

        // When
        Optional<UserDto> actual = underTest.signOut();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLoggedInUserShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        UserDto user = new UserDto("user", User.Role.USER);
        Optional<UserDto> expected = Optional.of(user);
        underTest = new UserServiceImpl(user, userRepository);

        // When
        Optional<UserDto> actual = underTest.getLoggedInUser();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLoggedInUserShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.getLoggedInUser();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignUpUserShouldThrowNullPointerExceptionWhenUsernameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signUp(null, "pass"));
    }

    @Test
    public void testSignUpUserShouldThrowNullPointerExceptionWhenPasswordIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signUp("user", null));
    }

    @Test
    public void testSignUpUserShouldCallUserRepositoryWhenTheInputIsValid() {
        // Given
        // When
        underTest.signUp("user", "pass");

        // Then
        verify(userRepository).save(new User("user", "pass", User.Role.USER));
    }

}