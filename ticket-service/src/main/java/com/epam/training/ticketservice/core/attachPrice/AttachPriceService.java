package com.epam.training.ticketservice.core.attachPrice;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

public interface AttachPriceService {
    void savePriceToRoom(PriceComponentDto priceComponentDto, RoomDto roomDto);

    void savePriceToMovie(PriceComponentDto priceComponentDto, MovieDto movieDto);

    void savePriceToScreening(PriceComponentDto priceComponentDto, ScreeningDto screeningDto);
}
