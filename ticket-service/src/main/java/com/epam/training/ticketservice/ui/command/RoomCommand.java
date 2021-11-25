package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final UserService userService;

    public RoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Add room to database")
    public String createRoom(String name, int rows, int cols) {
        try {
            RoomDto room = RoomDto.builder().name(name).rows(rows).columns(cols).build();
            roomService.addRoom(room);
            return room + " added to database.";
        } catch (Exception e) {
            return "Unsuccessful creating. The room is already in the database.";
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room",  value = "Update room's data")
    public String updateRoom(String name, int rows, int cols) {
        try {
            RoomDto roomDto = RoomDto.builder().name(name).rows(rows).columns(cols).build();
            roomService.updateRoom(roomDto);
            return roomDto + " is updated.";
        } catch (IllegalArgumentException e) {
            return "The room doesn't exist.";
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room", value = "Delete the given room")
    public String deleteRoom(String name) {
        Optional<RoomDto> roomDto = roomService.deleteRoom(name);
        if (roomDto.isPresent()) {
            return roomDto.get() + " deleted.";
        }
        return "The room doesn't exist in the database.";
    }

    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        List<RoomDto> roomList = roomService.listRooms();
        return roomList.isEmpty() ? "There are no rooms at the moment" :
                roomList
                        .stream()
                        .map(room -> room.toString())
                        .collect(Collectors.joining("\n"));
    }

    private Availability isAdmin() {
        Optional<UserDto> user = userService.getLoggedInUser();
        return user.isPresent() && user.get().getRole() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not signed in as admin!");
    }
}