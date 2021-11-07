package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.Movie;
import com.epam.training.ticketservice.core.room.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screening {

    private final String movieName;
    private final String roomName;
    private final Date date;

    public Screening(String movieName, String roomName, String date) throws ParseException {
        this.movieName = movieName;
        this.roomName = roomName;
        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.date = changeFormat.parse(date);
    }
}
