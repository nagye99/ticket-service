package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ScreeningServiceImplTest {

    private static final Movie IT_ENTITY = new Movie("It", "horror", 135);
    private static final Movie RUSH_ENTITY = new Movie("Rush", "drama", 123);
    private static final MovieDto IT_DTO = MovieDto.builder()
            .title("It")
            .genre("horror")
            .length(135)
            .build();

    private static final Room PEDERSOLI_ENTITY = new Room("Pedersoli", 10, 10);
    private static final Room LOUMIER_ENTITY = new Room("Loumier", 20, 23);
    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .name("Pedersoli")
            .roomRows(10)
            .roomColumns(10)
            .build();

    private static final Screening SCREENING_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING2_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING3_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 13:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING4_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING5_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 11:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING6_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final Screening SCREENING7_ENTITY = new Screening(1, "It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final ScreeningDto SCREENING_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    private static final ScreeningDto SCREENING2_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    private static final ScreeningDto SCREENING3_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 13:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    private static final ScreeningDto SCREENING4_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    private static final ScreeningDto SCREENING5_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 11:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    private static final ScreeningDto SCREENING6_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieService movieService = mock(MovieService.class);
    private final RoomService roomService = mock(RoomService.class);
    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(screeningRepository, movieService, roomService);

    @Test
    void testAddScreeningShouldThrowNullPointerExceptionWhenDateIsNull() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .movie(IT_DTO)
                .room(PEDERSOLI_DTO)
                .date(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addScreening(screeningDto));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningInAnotherScreeningDate() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING2_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningBeforeInAnotherScreeningDate() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING3_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningSameAnotherScreeningDate() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING4_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningInCleaning() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING5_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningInCleaningStart() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING6_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldThrowIllegalArgumentExceptionWhenScreeningAreSame() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of(SCREENING_ENTITY));
        when(movieService.getMinutes("It")).thenReturn(Long.valueOf(135));

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.addScreening(SCREENING_DTO));
    }

    @Test
    void testAddScreeningShouldCallScreeningRepositoryWhenTheInputDataIsCorrect() {
        // Given
        when(screeningRepository.findByRoomName(SCREENING_ENTITY.getRoomName())).thenReturn(List.of());

        // When

        underTest.addScreening(SCREENING_DTO);

        // Then
        verify(screeningRepository).save(SCREENING_ENTITY);
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenTitleIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening(null, "Pedersoli", LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening("It", null, LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenDateIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening("It", "Pedersoli", null));
    }

    @Test
    void testDeleteScreeningShouldCallScreeningRepositoryWhenTheInputScreeningIsValid() {
        // Given - When
        underTest.deleteScreening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // Then
        verify(screeningRepository).deleteByMovieTitleAndRoomNameAndDate("It", "Pedersoli", LocalDateTime.parse("2021-12-10 11:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    void testListScreeningsShouldListScreenings() {

        // Given
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_ENTITY, SCREENING2_ENTITY, SCREENING3_ENTITY, SCREENING4_ENTITY, SCREENING5_ENTITY, SCREENING6_ENTITY));
        when(movieService.getMovieByTitle("It")).thenReturn(IT_DTO);
        when(roomService.getRoomByName("Pedersoli")).thenReturn(PEDERSOLI_DTO);

        List<ScreeningDto> expected = List.of(SCREENING_DTO, SCREENING2_DTO, SCREENING3_DTO, SCREENING4_DTO, SCREENING5_DTO, SCREENING6_DTO);

        // When
        List<ScreeningDto> actual = underTest.listScreenings();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningIdShouldReturnInteger() {
        // Given
        when(screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(java.util.Optional.of(SCREENING7_ENTITY));

        Integer expected = 1;

        // When
        Integer actual = underTest.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningIdShouldThrowNullPointerException() {
        // Given
        when(screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate("Ironman", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(Optional.empty());

        // When -Then
        assertThrows(NullPointerException.class, () -> underTest.getScreeningId("Ironman", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }

    @Test
    void testGetNameByIdShouldReturnRoomName() {
        //Given
        when(screeningRepository.findById(1)).thenReturn(Optional.of(SCREENING_ENTITY));

        String expected = "Pedersoli";

        // When
        String actual = underTest.getNameById(1);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetNameByIdShouldThrowIllegalArgumentException() {
        //Given
        when(screeningRepository.findById(1)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.getNameById(1));
    }

    @Test
    void testGetScreeningByIdShouldReturnScreeningDto() {
        //Given
        when(screeningRepository.findById(1)).thenReturn(Optional.of(SCREENING_ENTITY));
        when(movieService.getMovieByTitle("It")).thenReturn(IT_DTO);
        when(roomService.getRoomByName("Pedersoli")).thenReturn(PEDERSOLI_DTO);

        ScreeningDto expected = SCREENING_DTO;

        // When
        ScreeningDto actual = underTest.getScreeningById(1);

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningByIdShouldThrowIllegalArgumentException() {
        //Given
        when(screeningRepository.findById(1)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.getScreeningById(1));
    }

    @Test
    void testGetScreeningByTitleRoomAndDateShouldReturnScreeningDto() {
        //Given
        when(screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))).thenReturn(Optional.of(SCREENING_ENTITY));
        when(movieService.getMovieByTitle("It")).thenReturn(IT_DTO);
        when(roomService.getRoomByName("Pedersoli")).thenReturn(PEDERSOLI_DTO);

        ScreeningDto expected = SCREENING_DTO;

        // When
        ScreeningDto actual = underTest.getScreeningByTitleRoomAndDate("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetScreeningByTitleRoomAndDateShouldThrowIllegalArgumentException() {
        //Given
        when(screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate("Ironman", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.getScreeningByTitleRoomAndDate("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
}