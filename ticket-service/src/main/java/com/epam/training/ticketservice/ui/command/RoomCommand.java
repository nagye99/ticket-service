package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final Availabilities availabilities;

    public RoomCommand(RoomService roomService, Availabilities availabilities) {
        this.roomService = roomService;
        this.availabilities = availabilities;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Add room to database")
    public String createRoom(String name, int rows, int cols) {
        try {
            RoomDto room = RoomDto.builder().name(name).roomRows(rows).roomColumns(cols).build();
            roomService.addRoom(room);
            return room + " added to database.";
        } catch (DataIntegrityViolationException e) {
            return "Unsuccessful creating. The room is already in the database.";
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room", value = "Update room's data")
    public String updateRoom(String name, int rows, int cols) {
        RoomDto roomDto = RoomDto.builder().name(name).roomRows(rows).roomColumns(cols).build();
        return roomService.updateRoom(roomDto);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room", value = "Delete the given room")
    public String deleteRoom(String name) {
        roomService.deleteRoom(name);
        return "The " + name + " now not exist in the database.";
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

    public Availability isAdmin() {
        return availabilities.isAdmin();
    }
}