package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.model.UserDto;

public interface BookingService {

    String bookSeats(UserDto user, ScreeningDto screeningDto, String seatsList);
}
