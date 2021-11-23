package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.attachPrice.AttachPriceService;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.price.PriceComponentService;
import com.epam.training.ticketservice.core.price.model.PriceComponentDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ShellComponent
public class PriceCommand {

    private final PriceComponentService priceComponentService;
    private final AttachPriceService attachPriceService;
    private final RoomService roomService;
    private final MovieService movieService;
    private final ScreeningService screeningService;

    public PriceCommand(PriceComponentService priceComponentService, AttachPriceService attachPriceService, RoomService roomService, MovieService movieService, ScreeningService screeningService) {
        this.priceComponentService = priceComponentService;
        this.attachPriceService = attachPriceService;
        this.roomService = roomService;
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "update base price", value = "Updating base price")
    public String updateBasePrice(int price) {
        return "Base price updated to: " + price;
    }

    @ShellMethod(key = "create price component", value = "Creating a new price component")
    public String createPriceComponent(String componentName, int price) {
        try {
            PriceComponentDto priceComponentDto = PriceComponentDto.builder().name(componentName).price(price).build();
            priceComponentService.savePriceComponent(priceComponentDto);
            return priceComponentDto + " is added to database.";
        } catch (Exception e) {
            return "The component is already exists.";
        }
    }

    @ShellMethod(key = "attach price component to room", value = "Price component attach to room")
    public String addPriceComponentToRoom(String componentName, String roomName) {
        Optional<PriceComponentDto> priceComponentDto = priceComponentService.getComponentByName(componentName);
        Optional<RoomDto> roomDto = roomService.getRoomByName(roomName);
        if (priceComponentDto.isPresent() && roomDto.isPresent()) {
        attachPriceService.savePriceToRoom(priceComponentDto.get(), roomDto.get());
        return "Price attached to room";
        } else {
            return "The room or priceComponent doesn't exist";
        }
    }

    @ShellMethod(key = "attach price component to movie", value = "Price component attach to movie")
    public String addPriceComponentToMovie(String componentName, String movieName) {
        Optional<PriceComponentDto> priceComponentDto = priceComponentService.getComponentByName(componentName);
        Optional<MovieDto> movieDto = movieService.getMovieByTitle(movieName);
        if (priceComponentDto.isPresent() && movieDto.isPresent()) {
            attachPriceService.savePriceToMovie(priceComponentDto.get(), movieDto.get());
            return "Price attached to movie";
        } else {
            return "The movie or priceComponent doesn't exist";
        }
    }

    @ShellMethod(key = "attach price component to screening", value = "Price component attach to screening")
    public String addPriceComponentToScreening(String componentName, String movieName, String roomName, String date) {
        Optional<PriceComponentDto> priceComponentDto = priceComponentService.getComponentByName(componentName);
        Optional<ScreeningDto> screeningDto = screeningService.getScreeningByTitleRoomAndDate(movieName,
                roomName,
                LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        if (priceComponentDto.isPresent() && screeningDto.isPresent()) {
            attachPriceService.savePriceToScreening(priceComponentDto.get(), screeningDto.get());
            return "Price attached to screening";
        } else {
            return "The movie or priceComponent doesn't exist";
        }
    }
}
