package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private final UserRepository userRepository;

    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        /*Movie satantango = new Movie("Sátántangó", "drama", 450);
        Movie spirit = new Movie("Spirited away", "animation", 75);
        movieRepository.saveAll(List.of(satantango, spirit));

        Room pedersoli = new Room("Pedersoli", 10, 15);
        Room loumier = new Room("Loumier", 20, 20);
        roomRepository.saveAll(List.of(pedersoli, loumier));

        Screening elso = new Screening("Sátántangó", "Pedersoli", LocalDateTime.parse("2021-11-12 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Screening masodik = new Screening("Sátántangó", "Loumier", LocalDateTime.parse("2021-11-15 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        screeningRepository.saveAll(List.of(elso, masodik));*/

        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
    }
}
