package com.epam.training.ticketservice.core.booking.model;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class BookingDto {
    private final ScreeningDto screening;
    private final String seats;
    private final Integer price;

    @Override
    public String toString() {
        return "Seats " + Stream.of(seats.split(" ")).map(seatPair -> "(" + seatPair + ")")
                .collect(Collectors.joining(", ")) + " on " + screening.getMovie().getTitle() + " in room "
                + screening.getRoom().getName() + " starting at "
                + screening.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " for "
                + price + " HUF";
    }
}
