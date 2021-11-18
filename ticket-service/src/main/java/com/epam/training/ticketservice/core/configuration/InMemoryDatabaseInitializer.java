package com.epam.training.ticketservice.core.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
//@Profile("!prod")
public class InMemoryDatabaseInitializer {

    /*private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public InMemoryDatabaseInitializer(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        Product videoCard = new Product("GPU", 600_000, "HUF");
        Product playStation5 = new Product("PS5", 500_000, "HUF");
        productRepository.saveAll(List.of(videoCard, playStation5));

        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
    }*/
}
