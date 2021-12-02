package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    @Transactional
    void deleteByMovieTitleAndRoomNameAndDate(String movieTitle, String roomName, LocalDateTime date);

    List<Screening> findByRoomName(String name);

    Optional<Screening> getScreeningByMovieTitleAndRoomNameAndDate(String movieTitle,
                                                                   String roomName,
                                                                   LocalDateTime date);

    Optional<Screening> findById(Integer id);
}
