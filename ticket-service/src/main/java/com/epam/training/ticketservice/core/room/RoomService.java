package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.Optional;

public interface RoomService {
    Optional<RoomDto> getRoomByName(String roomName);
}
