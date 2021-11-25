package com.epam.training.ticketservice.core.price.persistence.repository;

import com.epam.training.ticketservice.core.price.persistence.entity.AttachPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachPriceRepository extends JpaRepository<AttachPrice, Integer> {
    List<AttachPrice> getByObjectIdAndType(Integer objectId, AttachPrice.ObjectType type);
}
