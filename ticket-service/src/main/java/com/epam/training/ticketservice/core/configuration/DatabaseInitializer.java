package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import com.epam.training.ticketservice.core.price.persistence.repository.PriceComponentRepository;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private final UserRepository userRepository;
    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final PriceComponentRepository priceComponentRepository;

    public DatabaseInitializer(UserRepository userRepository,
                               ScreeningRepository screeningRepository,
                               RoomRepository roomRepository,
                               MovieRepository movieRepository,
                               PriceComponentRepository priceComponentRepository) {
        this.userRepository = userRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
        this.priceComponentRepository = priceComponentRepository;
    }

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

        PriceComponent priceComponent = new PriceComponent("base price", 1500);
        priceComponentRepository.save(priceComponent);

    }
}
