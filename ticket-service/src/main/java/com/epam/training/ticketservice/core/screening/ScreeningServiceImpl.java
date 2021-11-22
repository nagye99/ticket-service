package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieService movieService, RoomService roomService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @Override
    public void addScreening(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto.getDate(), "Date of screening cannot be null during saving!");
        Screening screening = new Screening(screeningDto.getMovie().getTitle(), screeningDto.getRoom().getName(), screeningDto.getDate());
        if (checkScreeningIsInOtherScreening(screening)) {
            throw new IllegalArgumentException("There is an overlapping screening");
        } else if (checkScreeningIsInCleaning(screening)) {
            throw new IllegalArgumentException("This would start in the break period after another screening in this room");
        } else {
            screeningRepository.save(screening);
        }
    }

    @Override
    public Optional<ScreeningDto> deleteScreening(String movieTitle, String roomName, LocalDateTime date) {
        Objects.requireNonNull(movieTitle, "MovieTitle of screening cannot be null during delete!");
        Objects.requireNonNull(roomName, "RoomName of screening cannot be null during delete!");
        Objects.requireNonNull(date, "Date of screening cannot be null during delete!");
        List<Screening> deletedScreenings = screeningRepository.deleteByMovieTitleAndRoomNameAndDate(movieTitle, roomName, date);
        Optional<Screening> screening = deletedScreenings.isEmpty() ? Optional.empty() : Optional.of(deletedScreenings.get(0));
        return convertScreeningEntityToScreeningDto(screening);
    }

    @Override
    public List<ScreeningDto> listScreenings() {
        return screeningRepository.findAll().stream().map(this::convertScreeningEntityToScreeningDto).collect(Collectors.toList());
    }

    @Override
    public Integer getScreeningId(String title, String roomName, LocalDateTime date) {
        Optional<Screening> screening = screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate(title, roomName, date);
        if (screening.isPresent()) {
            return screening.get().getId();
        }
        throw new NullPointerException("The screening doesn't exist.");
    }

    @Override
    public String getNameById(Integer id) {
        Optional<Screening> screening = screeningRepository.findById(id);
        return screening.get().getRoomName();
    }

    @Override
    public ScreeningDto getScreeningById(Integer id) {
        Screening screening = screeningRepository.getById(id);
        return convertScreeningEntityToScreeningDto(screening);
    }

    @Override
    public Optional<ScreeningDto> getScreeningByTitleRoomAndDate(String movieTitle, String roomName, LocalDateTime date) {
        Optional<Screening> screening = screeningRepository.getScreeningByMovieTitleAndRoomNameAndDate(movieTitle, roomName, date);
        return convertScreeningEntityToScreeningDto(screening);
    }

    private boolean checkScreeningIsInOtherScreening(Screening screening) {
        List<Screening> screenings = screeningRepository.findByRoomName(screening.getRoomName());
        List<List<LocalDateTime>> moviesStartAndEnd = screenings.stream()
                .map(screen -> List.of(screen.getDate(), screen.getDate().plusMinutes(movieService.getMinutes(screen.getMovieTitle()))))
                .collect(Collectors.toList());
        screenings.stream()
                .map(screen -> List.of(screen.getDate(), screen.getDate().plusMinutes(movieService.getMinutes(screen.getMovieTitle()))))
                .forEach(System.out::println);
        LocalDateTime currentMovieEnd = screening.getDate().plusMinutes(movieService.getMinutes(screening.getMovieTitle()));
        return moviesStartAndEnd.stream().anyMatch(startAndEnd -> (checkLocalDateBetween(screening.getDate(), startAndEnd.get(0), startAndEnd.get(1)) || checkLocalDateBetween(currentMovieEnd.plusMinutes(10), startAndEnd.get(0), startAndEnd.get(1))));
    }

    private boolean checkScreeningIsInCleaning(Screening screening) {
        List<Screening> screenings = screeningRepository.findByRoomName(screening.getRoomName());
        List<List<LocalDateTime>> cleanStartAndEnd = screenings.stream()
                .map(screen -> List.of(screen.getDate().plusMinutes(movieService.getMinutes(screen.getMovieTitle())), screen.getDate().plusMinutes(movieService.getMinutes(screen.getMovieTitle()) + 10)))
                .collect(Collectors.toList());
        return cleanStartAndEnd.stream().anyMatch(startAndEnd -> checkLocalDateBetween(screening.getDate(), startAndEnd.get(0), startAndEnd.get(1)));
    }

    private boolean checkLocalDateBetween(LocalDateTime currentDate, LocalDateTime startDate, LocalDateTime endDate) {
        return (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) || currentDate.isEqual(startDate) || currentDate.isEqual(endDate);
    }

    private ScreeningDto convertScreeningEntityToScreeningDto(Screening screening) {
        Optional<MovieDto> movie = movieService.getMovieByTitle(screening.getMovieTitle());
        Optional<RoomDto> room = roomService.getRoomByName(screening.getRoomName());
        if (movie.isPresent() && room.isPresent()) {
            return ScreeningDto.builder().movie(movie.get())
                        .room(room.get())
                        .date(screening.getDate())
                        .build();
        } else {
            throw new NullPointerException();
        }
    }

    private  Optional<ScreeningDto> convertScreeningEntityToScreeningDto(Optional<Screening> screening) {
        return screening.isEmpty() ? Optional.empty() : Optional.of(convertScreeningEntityToScreeningDto(screening.get()));
    }
}