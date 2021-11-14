package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomServiceImpl implements RoomService {

    private List<Room> roomList;

    @Override
    public Optional<Room> getRoomByName(String roomName) {
        return roomList.stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst();
    }
}
