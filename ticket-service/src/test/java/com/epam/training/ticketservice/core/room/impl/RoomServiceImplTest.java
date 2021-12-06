package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    private static final Room PEDERSOLI_ENTITY = new Room("Pedersoli", 10, 10);
    private static final Room LOUMIER_ENTITY = new Room("Loumier", 20, 23);
    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .name("Pedersoli")
            .roomRows(10)
            .roomColumns(10)
            .build();
    private static final RoomDto LOUMIER_DTO = RoomDto.builder()
            .name("Loumier")
            .roomRows(20)
            .roomColumns(23)
            .build();

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @Test
    void testAddRoomShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name(null)
                .roomRows(10)
                .roomColumns(10)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addRoom(roomDto));
    }

    @Test
    void testAddRoomShouldThrowNullPointerExceptionWhenRowIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name("Pedersoli")
                .roomRows(null)
                .roomColumns(10)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addRoom(roomDto));
    }

    @Test
    void testAddRoomShouldThrowNullPointerExceptionWhenColumnIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name("Pedersoli")
                .roomRows(10)
                .roomColumns(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addRoom(roomDto));
    }

    @Test
    public void testAddRoomShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.addRoom(null));
    }

    @Test
    public void testAddRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(PEDERSOLI_ENTITY)).thenReturn(PEDERSOLI_ENTITY);

        // When
        underTest.addRoom(PEDERSOLI_DTO);

        // Then
        verify(roomRepository).save(new Room("Pedersoli", 10, 10));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name(null)
                .roomColumns(10)
                .roomRows(15)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenColumnIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name("Pedersoli")
                .roomColumns(null)
                .roomRows(15)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenRowIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .name("Pedersoli")
                .roomColumns(10)
                .roomRows(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    public void testUpdateRoomShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.addRoom(null));
    }

    @Test
    public void testUpdateRoomShouldCallRoomRepositoryAndReturnStringWhenInputIsValid() {
        // Given
        when(roomRepository.save(new Room("Pedersoli", 12, 15))).thenReturn(new Room("Pedersoli", 12, 15));
        when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(PEDERSOLI_ENTITY));

        RoomDto UPDATED_PEDERSOLI_DTO = RoomDto
                .builder()
                .name("Pedersoli")
                .roomRows(12)
                .roomColumns(15)
                .build();

        String expected = UPDATED_PEDERSOLI_DTO + " is updated.";

        // When
        String actual = underTest.updateRoom(UPDATED_PEDERSOLI_DTO);

        // Then
        assertEquals(expected, actual);
        verify(roomRepository).save(new Room("Pedersoli", 12, 15));
        verify(roomRepository).findByName(UPDATED_PEDERSOLI_DTO.getName());
    }

    @Test
    public void testUpdateRoomShouldCallRoomRepositoryAndReturnStringWhenInputIsNotValid() {
        // Given
        when(roomRepository.save(PEDERSOLI_ENTITY)).thenReturn(PEDERSOLI_ENTITY);
        when(roomRepository.findByName(PEDERSOLI_ENTITY.getName())).thenReturn(Optional.empty());

        RoomDto UPDATED_JACK_DTO = RoomDto
                .builder()
                .name("JACK")
                .roomColumns(21)
                .roomRows(22)
                .build();

        String expected = "The room doesn't exist.";

        // When
        String actual = underTest.updateRoom(UPDATED_JACK_DTO);

        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findByName(UPDATED_JACK_DTO.getName());
    }

    @Test
    void testDeleteRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given - When
        underTest.deleteRoom("Pedersoli");

        // Then
        verify(roomRepository).deleteByName("Pedersoli");
    }

    @Test
    void testDeleteRoomShouldThrowNullPointerExceptionWhenRoomNameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteRoom(null));
    }


    @Test
    void testListRoomsShouldReturnRoomDtoList() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of(PEDERSOLI_ENTITY, LOUMIER_ENTITY));

        List<RoomDto> expected = List.of(PEDERSOLI_DTO, LOUMIER_DTO);

        // When
        List<RoomDto> actual = underTest.listRooms();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetRoomByNameShouldReturnRoomDto() {
        // Given
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_ENTITY));

        RoomDto expected = PEDERSOLI_DTO;

        // When
        RoomDto actual = underTest.getRoomByName("Pedersoli");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetRoomByNameShouldThrowIllegalArgumentExceptionWhenRoomNotExpect() {
        // Given
        when(roomRepository.findByName("Ironman")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.getRoomByName("Ironman"));
    }

    @Test
    void testGetRoomIdShouldReturnInteger() {
        // Given
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_ENTITY));

        Integer expected = PEDERSOLI_ENTITY.getId();

        // When
        Integer actual = underTest.getRoomId("Pedersoli");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetRoomIdShouldThrowNullPointerExceptionWhenRoomNotExpect() {
        // Given
        when(roomRepository.findByName("Jack")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.getRoomId("Jack"));
    }
}