package com.epam.training.ticketservice.core.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponent, Integer> {
}
