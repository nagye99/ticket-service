package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
//@Profile("!prod")
public class InMemoryDatabaseInitializer {

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public InMemoryDatabaseInitializer(MovieRepository movieRepository, RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    @PostConstruct
    public void init() {
         Movie satantango= new Movie("Sátántangó", "drama", 450);
         Movie spirit = new Movie("Spirited away", "animation", 75);
         movieRepository.saveAll(List.of(satantango, spirit));

        Room pedersoli= new Room("Pedersoli", 10, 15);
        Room loumier = new Room("Loumier", 20, 20);
        roomRepository.saveAll(List.of(pedersoli, loumier));

        Screening elso= new Screening("Sátántangó", "Pedersoli", LocalDateTime.parse("2021-11-12 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Screening masodik = new Screening("Sátántangó", "Loumier", LocalDateTime.parse("2021-11-15 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        screeningRepository.saveAll(List.of(elso, masodik));

        //User admin = new User("admin", "admin", User.Role.ADMIN);
        //userRepository.save(admin);
    }
}
