package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ScreeningDto {

    private final MovieDto movie;
    private final RoomDto room;
    private final LocalDateTime date;

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie .getGenre() + ", " + movie.getLength() + " minutes), screened in room " + room.getName() + ", at " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
