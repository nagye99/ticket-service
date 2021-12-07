package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDtoTest {
    private static final MovieDto IT_DTO = MovieDto.builder()
            .title("It")
            .genre("horror")
            .length(135)
            .build();

    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .name("Pedersoli")
            .roomRows(10)
            .roomColumns(10)
            .build();

    private static final ScreeningDto SCREENING_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();

    @Test
    void testToStringShouldReturnString(){
        //Given
        String expected = "It (horror, 135 minutes), screened in room Pedersoli, at 2021-12-10 14:00";

        //When
        String actual = SCREENING_DTO.toString();

        //Then
        assertEquals(expected, actual);
    }

}