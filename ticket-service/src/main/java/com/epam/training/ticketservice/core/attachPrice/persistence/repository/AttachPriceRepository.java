package com.epam.training.ticketservice.core.attachPrice.persistence.repository;

import com.epam.training.ticketservice.core.attachPrice.persistence.entity.AttachPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachPriceRepository extends JpaRepository<AttachPrice, Integer> {
}
