package com.epam.training.ticketservice.core.booking.impl;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.price.AttachPriceService;
import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private static final UserDto USER_DTO = new UserDto("a", User.Role.USER);

    private static final Movie IT_ENTITY = new Movie("It", "horror", 135);
    private static final Movie RUSH_ENTITY = new Movie("Rush", "drama", 123);
    private static final MovieDto IT_DTO = MovieDto.builder()
            .title("It")
            .genre("horror")
            .length(135)
            .build();

    private static final Room PEDERSOLI_ENTITY = new Room("Pedersoli", 10, 10);
    private static final Room LOUMIER_ENTITY = new Room("Loumier", 20, 23);
    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .name("Pedersoli")
            .roomRows(10)
            .roomColumns(10)
            .build();

    private static final Screening SCREENING_ENTITY = new Screening(1, "It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    private static final ScreeningDto SCREENING_DTO = ScreeningDto.builder()
            .room(PEDERSOLI_DTO)
            .movie(IT_DTO)
            .date(LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();

    private static final Booking BOOKING_ENTITY = new Booking(1, "a", 1, "5,5", 2500);
    private static final Booking BOOKING_ENTITY_WITHOUT_ID = new Booking( "a", 1, "5,5", 2500);
    private static final BookingDto BOOKING_DTO = BookingDto.builder()
            .screening(SCREENING_DTO)
            .seats("5,5")
            .price(2500)
            .build();

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final AttachPriceService attachPriceService = mock(AttachPriceService.class);
    private final ScreeningService screeningService = mock(ScreeningService.class);
    private final PriceComponentService priceComponentService = mock(PriceComponentService.class);
    private final RoomService roomService = mock(RoomService.class);
    private final BookingServiceImpl underTest = new BookingServiceImpl(bookingRepository, screeningService, roomService, priceComponentService, attachPriceService);

    @Test
    void testBookSeatsShouldReturnSeatIsNotInRoom() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of(BOOKING_ENTITY));

        String expected =  "Seat 12,12 does not exist in this room";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "12,12");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testBookSeatsShouldReturnSeatIsNotInRoom2() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of(BOOKING_ENTITY));

        String expected =  "Seat 0,12 does not exist in this room";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "0,12");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testBookSeatsShouldReturnSeatIsNotInRoom3() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of(BOOKING_ENTITY));

        String expected =  "Seat 5,12 does not exist in this room";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "5,12");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testBookSeatsShouldReturnSeatIsNotInRoom4() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of(BOOKING_ENTITY));

        String expected =  "Seat 5,0 does not exist in this room";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "5,0");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testBookSeatsShouldReturnSeatIsNotEmpty() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of(BOOKING_ENTITY));

        String expected =  "Seat (5,5) is already taken";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "5,5");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testBookSeatsShouldReturnBookedSuccesAndCallBookingRepository() {
        //Given
        when(screeningService.getScreeningId("It", "Pedersoli", LocalDateTime.parse("2021-12-10 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .thenReturn(1);
        when(screeningService.getNameById(1)).thenReturn(PEDERSOLI_DTO.getName());
        when(roomService.getRoomByName(PEDERSOLI_DTO.getName())).thenReturn(PEDERSOLI_DTO);
        when(bookingRepository.getByScreeningId(1)).thenReturn(List.of());
        when(priceComponentService.getBasePrice()).thenReturn(1500);
        when(attachPriceService.getPlusPrice(SCREENING_DTO)).thenReturn(1000);
        when(bookingRepository.save(BOOKING_ENTITY_WITHOUT_ID)).thenReturn(BOOKING_ENTITY);

        String expected =  "Seats booked: (5,5); the price for this booking is 2500 HUF";

        //When
        String actual = underTest.bookSeats(USER_DTO, SCREENING_DTO, "5,5");

        //Then
        assertEquals(expected, actual);
        verify(bookingRepository).save(BOOKING_ENTITY_WITHOUT_ID);
    }

    @Test
    void testGetBookingByUserShouldReturnBookingList() {
        //Given
        when(bookingRepository.getBookingsByUsername("a")).thenReturn(List.of(BOOKING_ENTITY));
        when(screeningService.getScreeningById(BOOKING_ENTITY.getScreeningId())).thenReturn(SCREENING_DTO);

        List<BookingDto> expected = List.of(BOOKING_DTO);

        //When
        List<BookingDto> actual = underTest.getBookingByUser("a");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetBookingByUserShouldReturnEmptyList() {
        //Given
        when(bookingRepository.getBookingsByUsername("a")).thenReturn(List.of());

        List<BookingDto> expected = List.of();

        //When
        List<BookingDto> actual = underTest.getBookingByUser("a");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    void testShowPriceShouldReturnInteger() {
        //Given
        when(priceComponentService.getBasePrice()).thenReturn(1500);
        when(attachPriceService.getPlusPrice(SCREENING_DTO)).thenReturn(1000);

        Integer expected = 5000;

        //When
        Integer actual = underTest.showPrice(SCREENING_DTO, "5,5 10,9");

        //Then
        assertEquals(expected, actual);
    }
}