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
public class ScreeningServiceImpl implements ScreeningService{

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
        Objects.requireNonNull(screeningDto.getMovie(), "Movie of screening cannot be null during saving!");
        Objects.requireNonNull(screeningDto.getRoom(), "Room of screening cannot be null during saving!");
        Objects.requireNonNull(screeningDto.getDate(), "Date of screening cannot be null during saving!");
        Screening screening = new Screening(screeningDto.getMovie().getTitle(), screeningDto.getRoom().getName(), screeningDto.getDate());
        screeningRepository.save(screening);
    }

    @Override
    public Optional<ScreeningDto> deleteScreening(String movieTitle, String roomName, LocalDateTime date) throws IllegalArgumentException {
        Objects.requireNonNull(movieTitle, "MovieTitle of screening cannot be null during delete!");
        Objects.requireNonNull(roomName, "RoomName of screening cannot be null during delete!");
        Objects.requireNonNull(date, "Date of screening cannot be null during delete!");
        List<Screening> deletedScreenings = screeningRepository.deleteByMovieTitleAndRoomNameAndDate(movieTitle, roomName, date);
        Optional<Screening> screening = deletedScreenings.isEmpty() ? Optional.empty() : Optional.of(deletedScreenings.get(0));
        return convertScreeningEntityToScreeningDto(screening);
    }

    @Override
    public List<ScreeningDto> listScreenings() throws IllegalArgumentException {
        return screeningRepository.findAll().stream().map(this::convertScreeningEntityToScreeningDto).collect(Collectors.toList());
    }

    private ScreeningDto convertScreeningEntityToScreeningDto(Screening screening) throws IllegalArgumentException {
        Optional<MovieDto> movie = movieService.getMovieByTitle(screening.getMovieTitle());
        Optional<RoomDto> room = roomService.getRoomByName(screening.getRoomName());
        if(movie.isPresent() && room.isPresent()){
            return ScreeningDto.builder().movie(movie.get())
                    .room(room.get())
                    .date(screening.getDate())
                    .build();
        }else{
            throw new IllegalArgumentException("Movie or Room doesn't exists.");
        }
    }

    private  Optional<ScreeningDto> convertScreeningEntityToScreeningDto(Optional<Screening> screening) throws IllegalArgumentException {
        return screening.isEmpty() ? Optional.empty() : Optional.of(convertScreeningEntityToScreeningDto(screening.get()));
    }
}
