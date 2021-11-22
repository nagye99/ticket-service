package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
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

    public BookingServiceImpl(BookingRepository bookingRepository, ScreeningService screeningService, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.screeningService = screeningService;
        this.roomService = roomService;
    }

    @Override
    public String bookSeats(UserDto user, ScreeningDto screeningDto, String seatsList) {
        Integer screeningId = screeningService.getScreeningId(screeningDto.getMovie().getTitle(), screeningDto.getRoom().getName(), screeningDto.getDate());
        Optional<String[]> notRealSeat = isSeatInRoom(screeningId, seatsList);
        Optional<String> bookedSeat = isSeatEnable(screeningId, seatsList);
        if (notRealSeat.isPresent()) {
            return "Seat " + notRealSeat.get()[0] + "," + notRealSeat.get()[1] + " does not exist in this room";
        } else if (bookedSeat.isPresent()) {
            return "Seat " + bookedSeat.get() + " is already taken";
        }
        Booking booking = new Booking(user.getUsername(), screeningId, seatsList);
        bookingRepository.save(booking);
        return "Seats booked: ";
    }

    private Optional<String[]> isSeatInRoom(Integer screeningId, String seatsList) {
        String roomName = screeningService.getNameById(screeningId);
        Optional<RoomDto> room = roomService.getRoomByName(roomName);//screeningService.getNameById(screeningId));
        if (room.isPresent()) {
            Integer rows = room.get().getRows();
            Integer columns = room.get().getColumns();

            List<String[]> seatPairs = Arrays.stream(seatsList.split(" ")).map(seatPair -> seatPair.split(",")).collect(Collectors.toList());
            return seatPairs.stream().filter(seatPair -> Integer.valueOf(seatPair[0]) > rows || Integer.valueOf(seatPair[0]) < 1 || Integer.valueOf(seatPair[1]) > columns || Integer.valueOf(seatPair[1]) < 0).findFirst();
        } else {
            throw new NullPointerException("Room doesn't exist.");
        }
    }

    private Optional<String> isSeatEnable(Integer screeningId, String seatsList) {
        List<Booking> bookings = bookingRepository.getByScreeningId(screeningId);
        List<String> bookedSeatPairs = bookings.stream().flatMap(booking -> Stream.of(booking.getSeats().split(" "))).collect(Collectors.toList());

        String[] seatPairs = seatsList.split(" ");
        return Arrays.stream(seatPairs).filter(seatPair -> bookedSeatPairs.stream().anyMatch(bookedSeatPair -> bookedSeatPair.equals(seatPair))).findFirst();
    }

    private BookingDto convertBookingEntityToBookingDto(Booking booking) {
        ScreeningDto screeningDto = screeningService.getScreeningById(booking.getScreeningId());
        List<String> seatPairs = List.of(booking.getSeats().split(" "));
        BookingDto bookingDto = BookingDto.builder().screening(screeningDto).seats(seatPairs).build();
        return bookingDto;
    }
}