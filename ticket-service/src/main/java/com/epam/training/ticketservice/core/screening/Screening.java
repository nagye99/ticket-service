package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.model.Room;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Screening {

    private final Movie movie;
    private final Room room;
    private final Date date;

    public Screening(Movie movie, Room room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie .getGenre() + ", " + movie.getLength() + " minutes), screened in room " + room.getName() + ", at " + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
    }
}
