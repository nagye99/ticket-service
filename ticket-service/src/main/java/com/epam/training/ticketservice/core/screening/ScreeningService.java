package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {
    void addScreening(ScreeningDto screeningDto);

    Optional<ScreeningDto> deleteScreening(String movieTitle, String roomName, LocalDateTime date) throws IllegalArgumentException;

    List<ScreeningDto> listScreenings();
}
