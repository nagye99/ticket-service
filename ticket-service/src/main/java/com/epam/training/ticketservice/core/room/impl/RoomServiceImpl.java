package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public String addRoom(RoomDto roomDto) {
        try {
            Objects.requireNonNull(roomDto.getName(), "Name of the room cannot be null during saving!");
            Objects.requireNonNull(roomDto.getColumns(), "Columns of chairs cannot be null during saving!");
            Objects.requireNonNull(roomDto.getRows(), "Rows of chairs cannot be null during saving!");
            Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
            roomRepository.save(room);
            return roomDto + " added to database.";
        } catch (Exception e) {
            return "Unsuccessful creating. The room is already in the database.";
        }
    }

    @Override
    public String updateRoom(RoomDto roomDto) {
        Optional<Room> roomOpt = roomRepository.findByName(roomDto.getName());
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setRows(roomDto.getRows());
            room.setColumns(roomDto.getColumns());
            roomRepository.save(room);
            return roomDto + " is updated.";
        } else {
            return "The room doesn't exist.";
        }
    }

    @Override
    public void deleteRoom(String name) {
        Objects.requireNonNull(name, "Title of movie cannot be null during delete!");
        roomRepository.deleteByName(name);
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream().map(this::convertRoomEntityToRoomDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomByName(String name) {
        Optional<RoomDto> roomDto = convertRoomEntityToRoomDto(roomRepository.findByName(name));
        if(roomDto.isPresent()) {
            return roomDto.get();
        } else {
            throw new IllegalArgumentException("The room doesn't exist");
        }
    }

    @Override
    public Integer getRoomId(String name) {
        Optional<Room> room =
                roomRepository.findByName(name);
        if (room.isPresent()) {
            return room.get().getId();
        }
        throw new NullPointerException("The room doesn't exist.");
    }

    private RoomDto convertRoomEntityToRoomDto(Room room) {
        return RoomDto.builder().name(room.getName()).rows(room.getRows()).columns(room.getColumns()).build();
    }

    private  Optional<RoomDto> convertRoomEntityToRoomDto(Optional<Room> room) {
        return room.isEmpty() ? Optional.empty() : Optional.of(convertRoomEntityToRoomDto(room.get()));
    }
}
