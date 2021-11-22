package com.epam.training.ticketservice.core.booking.model;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookingDto {
    private final ScreeningDto screening;
    private final List<String> seats;
}
