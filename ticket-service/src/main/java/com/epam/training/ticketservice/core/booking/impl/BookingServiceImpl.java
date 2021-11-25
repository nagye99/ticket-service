package com.epam.training.ticketservice.core.booking.impl;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.price.AttachPriceService;
import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ScreeningService screeningService;
    private final RoomService roomService;
    private final PriceComponentService priceComponentService;
    private final AttachPriceService attachPriceService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              ScreeningService screeningService,
                              RoomService roomService,
                              PriceComponentService priceComponentService,
                              AttachPriceService attachPriceService) {
        this.bookingRepository = bookingRepository;
        this.screeningService = screeningService;
        this.roomService = roomService;
        this.priceComponentService = priceComponentService;
        this.attachPriceService = attachPriceService;
    }

    @Override
    public String bookSeats(UserDto user, ScreeningDto screeningDto, String seatsList) {
        Integer screeningId = screeningService.getScreeningId(screeningDto.getMovie().getTitle(),
                screeningDto.getRoom().getName(),
                screeningDto.getDate());
        Optional<String[]> notRealSeat = isSeatInRoom(screeningId, seatsList);
        Optional<String> bookedSeat = isSeatEnable(screeningId, seatsList);
        if (notRealSeat.isPresent()) {
            return "Seat " + notRealSeat.get()[0] + "," + notRealSeat.get()[1] + " does not exist in this room";
        } else if (bookedSeat.isPresent()) {
            return "Seat (" + bookedSeat.get() + ") is already taken";
        }
        Integer price = calculatePrice(screeningDto, seatsList);
        Booking booking = new Booking(user.getUsername(), screeningId, seatsList, price);
        bookingRepository.save(booking);
        return "Seats booked: " + Stream.of(seatsList.split(" ")).map(seatPair -> "(" + seatPair + ")")
                .collect(Collectors.joining(", ")) + "; the price for this booking is " + price + " HUF";
    }

    @Override
    public List<BookingDto> getBookingByUser(String username) {
        List<Booking> bookings = bookingRepository.getBookingsByUsername(username);
        return bookings.stream().map(this::convertBookingEntityToBookingDto).collect(Collectors.toList());
    }

    @Override
    public Integer showPrice(ScreeningDto screeningDto, String seatsList) {
        return  calculatePrice(screeningDto, seatsList);
    }

    private Optional<String[]> isSeatInRoom(Integer screeningId, String seatsList) {
        String roomName = screeningService.getNameById(screeningId);
        Optional<RoomDto> room = roomService.getRoomByName(roomName);//screeningService.getNameById(screeningId));
        if (room.isPresent()) {
            Integer rows = room.get().getRows();
            Integer columns = room.get().getColumns();

            List<String[]> seatPairs = Arrays.stream(seatsList.split(" "))
                    .map(seatPair -> seatPair.split(","))
                    .collect(Collectors.toList());
            return seatPairs
                    .stream()
                    .filter(seatPair -> Integer.valueOf(seatPair[0]) > rows || Integer.valueOf(seatPair[0]) < 1
                            || Integer.valueOf(seatPair[1]) > columns || Integer.valueOf(seatPair[1]) < 0).findFirst();
        } else {
            throw new NullPointerException("Room doesn't exist.");
        }
    }

    private Optional<String> isSeatEnable(Integer screeningId, String seatsList) {
        List<Booking> bookings = bookingRepository.getByScreeningId(screeningId);
        List<String> bookedSeatPairs = bookings
                .stream()
                .flatMap(booking -> Stream.of(booking.getSeats()
                        .split(" ")))
                .collect(Collectors.toList());

        String[] seatPairs = seatsList.split(" ");
        return Arrays
                .stream(seatPairs)
                .filter(seatPair -> bookedSeatPairs
                        .stream()
                        .anyMatch(bookedSeatPair -> bookedSeatPair.equals(seatPair)))
                .findFirst();
    }

    private Integer calculatePrice(ScreeningDto screeningDto, String seatsList) {
        Integer personPrice = priceComponentService.getBasePrice() + attachPriceService.getPlusPrice(screeningDto);
        Integer seatCount = Math.toIntExact(Arrays.stream(seatsList.split(" ")).count());
        Integer price = personPrice * seatCount;
        return price;
    }

    private BookingDto convertBookingEntityToBookingDto(Booking booking) {
        ScreeningDto screeningDto = screeningService.getScreeningById(booking.getScreeningId());
        BookingDto bookingDto = BookingDto
                .builder()
                .screening(screeningDto)
                .seats(booking.getSeats())
                .price(booking.getPrice())
                .build();
        return bookingDto;
    }
}