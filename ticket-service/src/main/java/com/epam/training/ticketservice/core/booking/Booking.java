package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.util.List;

public class Booking {
    private final ScreeningDto screening;
    private final List<String> seats;

    public Booking(ScreeningDto screening, List<String> seats) {
        this.screening = screening;
        this.seats = seats;
    }
}
