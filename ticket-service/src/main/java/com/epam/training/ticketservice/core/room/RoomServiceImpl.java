package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addRoom(RoomDto roomDto) {
        Objects.requireNonNull(roomDto.getName(), "Name of the room cannot be null during saving!");
        Objects.requireNonNull(roomDto.getColumns(), "Columns of chairs cannot be null during saving!");
        Objects.requireNonNull(roomDto.getRows(), "Rows of chairs cannot be null during saving!");
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
        roomRepository.save(room);
    }

    @Override
    public Optional<RoomDto> updateRoom(String name, Integer cols, Integer rows) {
        return Optional.empty();
    }

    @Override
    public Optional<RoomDto> deleteRoom(String name) {
        Objects.requireNonNull(name, "Title of movie cannot be null during delete!");
        List<Room> deletedRooms = roomRepository.deleteByName(name);
        Optional<Room> room = deletedRooms.isEmpty() ? Optional.empty() : Optional.of(deletedRooms.get(0));
        return convertRoomEntityToRoomDto(room);
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream().map(this::convertRoomEntityToRoomDto).collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDto> getRoomByName(String name) {
        return convertRoomEntityToRoomDto(roomRepository.findByName(name));
    }

    private RoomDto convertRoomEntityToRoomDto(Room room){
        return RoomDto.builder().name (room.getName()).rows(room.getRows()).columns(room.getColumns()).build();
    }

    private  Optional<RoomDto> convertRoomEntityToRoomDto(Optional<Room> room){
        return room.isEmpty() ? Optional.empty() : Optional.of(convertRoomEntityToRoomDto(room.get()));
    }
}
