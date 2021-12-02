package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {

    String addRoom(RoomDto roomDto);

    String updateRoom(RoomDto roomDto);

    void deleteRoom(String title);

    List<RoomDto> listRooms();

    RoomDto getRoomByName(String name);

    Integer getRoomId(String name);
}
