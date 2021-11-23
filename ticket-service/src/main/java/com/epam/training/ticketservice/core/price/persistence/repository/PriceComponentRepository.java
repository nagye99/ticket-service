package com.epam.training.ticketservice.core.price.persistence.repository;

import com.epam.training.ticketservice.core.price.persistence.entity.PriceComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponent, Integer> {
    Optional<PriceComponent> findByName(String componentNe);
}
