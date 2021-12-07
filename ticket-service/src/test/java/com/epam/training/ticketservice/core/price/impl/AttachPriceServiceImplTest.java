package com.epam.training.ticketservice.core.price.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.price.model.AttachPriceDto;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.price.persistence.entity.AttachPrice;
import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import com.epam.training.ticketservice.core.price.persistence.repository.AttachPriceRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttachPriceServiceImplTest {

    private static final Movie IT_ENTITY = new Movie("It", "horror", 135);
    private static final MovieDto IT_DTO = MovieDto.builder()
            .title("It")
            .genre("horror")
            .length(135)
            .build();

    private static final Room PEDERSOLI_ENTITY = new Room("Pedersoli", 10, 10);
    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .name("Pedersoli")
            .roomRows(10)
            .roomColumns(10)
            .build();


    private static final Screening SCREENING_ENTITY = new Screening("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final ScreeningDto SCREENING_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();

    private static final PriceComponent PRICE_COMPONENT_ENTITY = new PriceComponent("a", 1000);
    private static final PriceComponentDto PRICE_COMPONENT_DTO = PriceComponentDto.builder()
            .name("a")
            .price(1000)
            .build();

    private static final PriceComponent BASE_PRICE_ENTITY = new PriceComponent("base price", 1500);
    private static final PriceComponentDto BASE_PRICE_DTO = PriceComponentDto.builder()
            .name("base price")
            .price(1500)
            .build();

    private static final AttachPrice ATTACH_PRICE_TO_ROOM_ENTITY = new AttachPrice("a", 1, AttachPrice.ObjectType.ROOM);
    private static final AttachPriceDto ATTACH_PRICE_TO_ROOM_DTO = AttachPriceDto.builder()
            .price(1000)
            .build();

    private static final AttachPrice ATTACH_PRICE_TO_MOVIE_ENTITY = new AttachPrice("a", 1, AttachPrice.ObjectType.MOVIE);
    private static final AttachPriceDto ATTACH_PRICE_TO_MOVIE_DTO = AttachPriceDto.builder()
            .price(1000)
            .build();

    private static final AttachPrice ATTACH_PRICE_TO_SCREENING_ENTITY = new AttachPrice("a", 1, AttachPrice.ObjectType.SCREENING);
    private static final AttachPriceDto ATTACH_PRICE_TO_SCRENNING_DTO = AttachPriceDto.builder()
            .price(1000)
            .build();

    private final AttachPriceRepository attachPriceRepository = mock(AttachPriceRepository.class);
    private final MovieService movieService = mock(MovieService.class);
    private final RoomService roomService = mock(RoomService.class);
    private final ScreeningService screeningService = mock(ScreeningService.class);
    private final PriceComponentService priceComponentService = mock(PriceComponentService.class);
    private final AttachPriceServiceImpl underTest = new AttachPriceServiceImpl(attachPriceRepository, roomService, movieService, screeningService, priceComponentService);


    @Test
    void testSavePriceToRoomShouldCallAttachPriceRepository(){
        //Given
        when(roomService.getRoomId(PEDERSOLI_DTO.getName())).thenReturn(1);
        when(attachPriceRepository.save(ATTACH_PRICE_TO_ROOM_ENTITY)).thenReturn(ATTACH_PRICE_TO_ROOM_ENTITY);

        //When
        underTest.savePriceToRoom(PRICE_COMPONENT_DTO, PEDERSOLI_DTO);

        //Then
        verify(attachPriceRepository).save(ATTACH_PRICE_TO_ROOM_ENTITY);
    }

    @Test
    void testSavePriceToMovieShouldCallAttachPriceRepository(){
        //Given
        when(movieService.getMovieId(IT_DTO.getTitle())).thenReturn(1);
        when(attachPriceRepository.save(ATTACH_PRICE_TO_MOVIE_ENTITY)).thenReturn(ATTACH_PRICE_TO_MOVIE_ENTITY);

        //When
        underTest.savePriceToMovie(PRICE_COMPONENT_DTO, IT_DTO);

        //Then
        verify(attachPriceRepository).save(ATTACH_PRICE_TO_MOVIE_ENTITY);
    }

    @Test
    void testSavePriceToScreeningShouldCallAttachPriceRepository(){
        //Given
        when(screeningService.getScreeningId(SCREENING_DTO.getMovie().getTitle(), SCREENING_DTO.getRoom().getName(), SCREENING_DTO.getDate())).thenReturn(1);
        when(attachPriceRepository.save(ATTACH_PRICE_TO_SCREENING_ENTITY)).thenReturn(ATTACH_PRICE_TO_SCREENING_ENTITY);

        //When
        underTest.savePriceToScreening(PRICE_COMPONENT_DTO, SCREENING_DTO);

        //Then
        verify(attachPriceRepository).save(ATTACH_PRICE_TO_SCREENING_ENTITY);
    }

    @Test
    void testGetPlusPriceShouldReturnPlusPriceInteger() {
        //Given
        when(movieService.getMovieId(SCREENING_DTO.getMovie().getTitle())).thenReturn(1);
        when(attachPriceRepository.getByObjectIdAndType(1, AttachPrice.ObjectType.MOVIE)).thenReturn(List.of(ATTACH_PRICE_TO_MOVIE_ENTITY));
        when(priceComponentService.getPriceByComponentName("a")).thenReturn(1000);
        when(roomService.getRoomId(SCREENING_DTO.getRoom().getName())).thenReturn(1);
        when(attachPriceRepository.getByObjectIdAndType(1, AttachPrice.ObjectType.ROOM)).thenReturn(List.of(ATTACH_PRICE_TO_ROOM_ENTITY));
        when(priceComponentService.getPriceByComponentName("a")).thenReturn(1000);
        when(screeningService.getScreeningId(SCREENING_DTO.getMovie().getTitle(), SCREENING_DTO.getRoom().getName(), SCREENING_DTO.getDate())).thenReturn(1);
        when(attachPriceRepository.getByObjectIdAndType(1, AttachPrice.ObjectType.SCREENING)).thenReturn(List.of(ATTACH_PRICE_TO_SCREENING_ENTITY));
        when(priceComponentService.getPriceByComponentName("a")).thenReturn(1000);

        Integer expected = 3000;

        //When
        Integer actual = underTest.getPlusPrice(SCREENING_DTO);

        //Then
        assertEquals(expected, actual);
    }
}