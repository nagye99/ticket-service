package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RoomCommand {

    @ShellMethod(key = "create room", value = "Add room to database")
    public RoomDto createRoom(String roomName, int rows, int cols) {
        return new RoomDto(roomName, rows, cols);
    }

    @ShellMethod(key = "update room",  value ="Update room's data")
    public String updateRoom(String roomName, int rows, int cols) {
        return new RoomDto(roomName, rows, cols) + " is updated.";
    }

    @ShellMethod(key = "delete room", value = "Delete the given room")
    public String deleteRoom(String roomName) {
        return roomName + " is deleted.";
    }

    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        return "Rooms list";
    }
}