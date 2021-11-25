package com.epam.training.ticketservice.core.price.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.price.AttachPriceService;
import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.price.persistence.entity.AttachPrice;
import com.epam.training.ticketservice.core.price.persistence.repository.AttachPriceRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttachPriceServiceImpl implements AttachPriceService {

    private final AttachPriceRepository attachPriceRepository;
    private final RoomService roomService;
    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final PriceComponentService priceComponentService;

    public AttachPriceServiceImpl(AttachPriceRepository attachPriceRepository,
                                  RoomService roomService,
                                  MovieService movieService,
                                  ScreeningService screeningService,
                                  PriceComponentService priceComponentService) {
        this.attachPriceRepository = attachPriceRepository;
        this.roomService = roomService;
        this.movieService = movieService;
        this.screeningService = screeningService;
        this.priceComponentService = priceComponentService;
    }

    @Override
    public void savePriceToRoom(PriceComponentDto priceComponentDto, RoomDto roomDto) {
        Integer roomId = roomService.getRoomId(roomDto.getName());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(), roomId, AttachPrice.ObjectType.ROOM);
        attachPriceRepository.save(attachPrice);
    }

    @Override
    public void savePriceToMovie(PriceComponentDto priceComponentDto, MovieDto movieDto) {
        Integer movieId = movieService.getMovieId(movieDto.getTitle());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(), movieId, AttachPrice.ObjectType.MOVIE);
        attachPriceRepository.save(attachPrice);
    }

    @Override
    public void savePriceToScreening(PriceComponentDto priceComponentDto, ScreeningDto screeningDto) {
        Integer screeningId = screeningService.getScreeningId(screeningDto.getMovie().getTitle(),
                screeningDto.getRoom().getName(),
                screeningDto.getDate());
        AttachPrice attachPrice = new AttachPrice(priceComponentDto.getName(),
                screeningId,
                AttachPrice.ObjectType.SCREENING);
        attachPriceRepository.save(attachPrice);
    }

    @Override
    public Integer getPlusPrice(ScreeningDto screeningDto) {
        Integer plusPrice = 0;
        plusPrice += getPlusPriceToMovie(screeningDto.getMovie().getTitle());
        plusPrice += getPlusPriceToRoom(screeningDto.getRoom().getName());
        plusPrice += getPlusPriceToScreening(screeningDto.getMovie().getTitle(),
                screeningDto.getRoom().getName(),
                screeningDto.getDate());
        return plusPrice;
    }

    private Integer getPlusPriceToMovie(String title) {
        Integer plusPrice = 0;
        Integer movieId = movieService.getMovieId(title);
        List<AttachPrice> attachPrices = attachPriceRepository
                .getByObjectIdAndType(movieId, AttachPrice.ObjectType.MOVIE);
        return attachPrices
                .stream()
                .map(attachPrice -> priceComponentService.getPriceByComponentName(attachPrice.getPriceComponentName()))
                .reduce(0, (subtotal, element) -> subtotal + element);
    }

    private Integer getPlusPriceToRoom(String name) {
        Integer plusPrice = 0;
        Integer roomId = roomService.getRoomId(name);
        List<AttachPrice> attachPrices = attachPriceRepository
                .getByObjectIdAndType(roomId, AttachPrice.ObjectType.ROOM);
        return attachPrices
                .stream()
                .map(attachPrice -> priceComponentService.getPriceByComponentName(attachPrice.getPriceComponentName()))
                .reduce(0, (subtotal, element) -> subtotal + element);
    }

    private Integer getPlusPriceToScreening(String title, String name, LocalDateTime date) {
        Integer plusPrice = 0;
        Integer screeningId = screeningService.getScreeningId(title, name, date);
        List<AttachPrice> attachPrices = attachPriceRepository
                .getByObjectIdAndType(screeningId, AttachPrice.ObjectType.SCREENING);
        return attachPrices
                .stream()
                .map(attachPrice -> priceComponentService.getPriceByComponentName(attachPrice.getPriceComponentName()))
                .reduce(0, (subtotal, element) -> subtotal + element);
    }
}
