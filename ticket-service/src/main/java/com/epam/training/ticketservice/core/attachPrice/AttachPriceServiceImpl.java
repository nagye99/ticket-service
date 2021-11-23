package com.epam.training.ticketservice.core.attachPrice;

import com.epam.training.ticketservice.core.attachPrice.persistence.entity.AttachPrice;
import com.epam.training.ticketservice.core.attachPrice.persistence.repository.AttachPriceRepository;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.stereotype.Service;

@Service
public class AttachPriceServiceImpl implements AttachPriceService {

    private final AttachPriceRepository attachPriceRepository;
    private final RoomService roomService;
    private final MovieService movieService;
    private final ScreeningService screeningService;

    public AttachPriceServiceImpl(AttachPriceRepository attachPriceRepository, RoomService roomService, MovieService movieService, ScreeningService screeningService) {
        this.attachPriceRepository = attachPriceRepository;
        this.roomService = roomService;
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    @Override
    public void savePriceToRoom(PriceComponentDto priceComponentDto, RoomDto roomDto){
        Integer roomId = roomService.getRoomId(roomDto.getName());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(), roomId, AttachPrice.ObjectType.ROOM);
        attachPriceRepository.save(attachPrice);
    }

    @Override
    public void savePriceToMovie(PriceComponentDto priceComponentDto, MovieDto movieDto){
        Integer movieId = movieService.getMovieId(movieDto.getTitle());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(), movieId, AttachPrice.ObjectType.MOVIE);
        attachPriceRepository.save(attachPrice);
    }

    @Override
    public void savePriceToScreening(PriceComponentDto priceComponentDto, ScreeningDto screeningDto){
        Integer screeningId = screeningService.getScreeningId(screeningDto.getMovie().getTitle(),
                screeningDto.getRoom().getName(),
                screeningDto.getDate());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(), screeningId, AttachPrice.ObjectType.MOVIE);
        attachPriceRepository.save(attachPrice);
    }
}
