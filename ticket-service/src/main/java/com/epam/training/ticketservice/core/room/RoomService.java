package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void addRoom(RoomDto roomDto);

    void updateRoom(RoomDto roomDto);

    Optional<RoomDto> deleteRoom(String title);

    List<RoomDto> listRooms();

    Optional<RoomDto> getRoomByName(String name);

    Integer getRoomId(String name);
}
