package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.model.UserDto;

import java.util.List;

public interface BookingService {

    String bookSeats(UserDto user, ScreeningDto screeningDto, String seatsList);

    List<BookingDto> getBookingByUser(String username);

    Integer showPrice(ScreeningDto screeningDto, String seatsList);
}
