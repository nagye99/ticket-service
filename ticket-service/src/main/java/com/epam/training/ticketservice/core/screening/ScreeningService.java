package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    void addScreening(ScreeningDto screeningDto);

    Optional<ScreeningDto> deleteScreening(String movieTitle, String roomName, LocalDateTime date);

    List<ScreeningDto> listScreenings();

    Integer getScreeningId(String title, String roomName, LocalDateTime date);

    String getNameById(Integer id);

    ScreeningDto getScreeningById(Integer id);

    Optional<ScreeningDto> getScreeningByTitleRoomAndDate(String movieTitle, String roomName, LocalDateTime date);
}
