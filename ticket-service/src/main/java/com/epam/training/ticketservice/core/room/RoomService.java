package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;

import java.util.Optional;

public interface RoomService {
    Optional<Room> getRoomByName(String roomName);
}
