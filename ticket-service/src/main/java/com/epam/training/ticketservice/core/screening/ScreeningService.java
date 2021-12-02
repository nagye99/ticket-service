package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {

    void addScreening(ScreeningDto screeningDto);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime date);

    List<ScreeningDto> listScreenings();

    Integer getScreeningId(String title, String roomName, LocalDateTime date);

    String getNameById(Integer id);

    ScreeningDto getScreeningById(Integer id);

    ScreeningDto getScreeningByTitleRoomAndDate(String movieTitle, String roomName, LocalDateTime date);
}
