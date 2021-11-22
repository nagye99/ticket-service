package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;

    public RoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

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

    @ShellMethod(key = "update room",  value = "Update room's data")
    public String updateRoom(String name, int rows, int cols) {
        return RoomDto.builder().name(name).rows(rows).columns(cols).build() + " is updated.";
    }

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
        return roomList.isEmpty() ? "There are no rooms at the moment" : roomList.stream().map(room -> room.toString()).collect(Collectors.joining("\n"));
    }
}